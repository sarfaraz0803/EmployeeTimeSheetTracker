package com.axis.restcrud.controller

import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.Account
import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.ManagerAuth
import com.axis.restcrud.service.AccountServiceImpl
import com.axis.restcrud.service.ManageServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
@RequestMapping("/manager")
class ManagerController {

    @Autowired
    private lateinit var managerServiceImpl:ManageServiceImpl
    @Autowired
    private lateinit var accountServiceImpl: AccountServiceImpl
    private val passwordEncoder = BCryptPasswordEncoder()

    private lateinit var appSecret: String

    //These register, login, logout functions are for manager

    @PostMapping("/register")
    fun registerManager(@RequestBody managerAuth:ManagerAuth): ResponseEntity<ManagerAuth>{
        return ResponseEntity(managerServiceImpl.registerManager(managerAuth),HttpStatus.OK)
    }

    @PostMapping("/login")
    fun managerAuthentication(@RequestBody managerAuth: ManagerAuth, response : HttpServletResponse): ResponseEntity<Any>{
        val manLogin = this.managerServiceImpl.loginManager(managerAuth)
        if(manLogin != null){
            if(passwordEncoder.matches(managerAuth.password,manLogin.password)){

                val issuer = manLogin.username
                appSecret = manLogin.username
                val jwt = Jwts.builder()
                    .setIssuer(issuer)
                    .setExpiration(Date(System.currentTimeMillis() + 60 * 1000 * 24))   // 1 Day
                    .signWith(SignatureAlgorithm.HS512, appSecret)
                    .compact()

                val cookie = Cookie("jwtManager", jwt)
                cookie.isHttpOnly = true
                //cookie.secure = true

                response.addCookie(cookie)

                return ResponseEntity("Successfully LoggedIn",HttpStatus.OK)
            }else{
                throw Warning("Invalid Password")
            }
        }else{
            return ResponseEntity("Manager is NULL",HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("Please Give Credentials",HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/loggedManager")
    fun loggedManager(@CookieValue("jwtManager") jwt:String? ):ResponseEntity<Any>{
        try{
            if(jwt == null){
                return ResponseEntity("No logged Manager",HttpStatus.UNAUTHORIZED)
            }
            val body = Jwts.parser().setSigningKey(appSecret).parseClaimsJws(jwt).body
            return ResponseEntity(managerServiceImpl.getLoggedManager(body.issuer),HttpStatus.OK)
        }catch(e:Exception){
            return ResponseEntity(e,HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/logout")
    fun logOutManager(@CookieValue("jwtManager") jwt:String?, response: HttpServletResponse):ResponseEntity<Any>{
        if(jwt != null){
            val cookie = Cookie("jwtManager", jwt)
            cookie.maxAge = 0
            response.addCookie(cookie)
        }else {
            return ResponseEntity("LogIn first", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("Successfully LoggedOut", HttpStatus.OK)

    }

    @DeleteMapping("/deleteCredentials/{id}")
    fun deleteCredentialDetails(@PathVariable id: Int):ResponseEntity<Any>{
        return ResponseEntity(managerServiceImpl.deleteCredentialsDetails(id),HttpStatus.OK)
    }

    @PostMapping("/registerEmployee")
    fun registerManager(@RequestBody empCre:EmpCre): ResponseEntity<EmpCre>{
        return ResponseEntity(managerServiceImpl.registerEmployee(empCre),HttpStatus.OK)
    }


    // employeeAccount operations--------------------------------------------------------------------------
    //These functions are available to the manager after successful login/authentication


    @PostMapping("/addAccount")
    fun addNewAccount(@CookieValue("jwtManager") jwt: String?, @Valid @RequestBody account: Account): ResponseEntity<Any>{
        try {
            if (jwt == null) {
                return ResponseEntity("LogIn First", HttpStatus.BAD_REQUEST)
            } else {
                val acc = accountServiceImpl.addAccount(account)
                return ResponseEntity(acc, HttpStatus.OK)
            }
        }catch (e:Exception){
            return ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/getAllAccounts")
    fun getAccounts(@CookieValue("jwtManager") jwt: String?): ResponseEntity<Any>{
        try {
            if (jwt == null) {
                return ResponseEntity("LogIn First", HttpStatus.BAD_REQUEST)
            } else {
                val accList = accountServiceImpl.getAllAccount()
                return ResponseEntity(accList, HttpStatus.OK)
            }
        }catch (e:Exception){
            return ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/getAccountById/{id}")
    fun getAccountById(@CookieValue("jwtManager") jwt: String?, @PathVariable id:Int): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity("LogIn First", HttpStatus.BAD_REQUEST)
            } else {
                return ResponseEntity(accountServiceImpl.getAccountById(id), HttpStatus.OK)
            }
        }catch (e:Exception){
            return ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/updateAccountById/{id}")
    fun updateAccountById(@CookieValue("jwtManager") jwt: String?, @PathVariable id: Int, @Valid @RequestBody account: Account): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity("LogIn First", HttpStatus.BAD_REQUEST)
            } else {
                val upAcc = accountServiceImpl.updateAccountById(id,account)
                return ResponseEntity(upAcc, HttpStatus.OK)
            }
        }catch (e:Exception){
            return ResponseEntity(e.toString(),HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/deleteAccountById/{id}")
    fun deleteAccountById(@CookieValue("jwtManager") jwt: String?, @PathVariable id: Int): ResponseEntity<Any>{
        try {
            if (jwt == null) {
                return ResponseEntity("LogIn First", HttpStatus.BAD_REQUEST)
            } else {
                return ResponseEntity(accountServiceImpl.deleteAccountById(id), HttpStatus.OK)
            }
        }catch (e:Exception) {
            return ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST)
        }
    }


}