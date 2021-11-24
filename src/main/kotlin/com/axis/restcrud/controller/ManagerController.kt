package com.axis.restcrud.controller

import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.Account
import com.axis.restcrud.modal.ManagerAuth
import com.axis.restcrud.service.AccountServiceImpl
import com.axis.restcrud.service.ManageServiceImpl
import io.jsonwebtoken.Jwt
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

                val issuer = manLogin._id.toString()
                val jwt = Jwts.builder()
                    .setIssuer(issuer)
                    .setExpiration(Date(System.currentTimeMillis() + 60 * 1000 *24 ))
                    .signWith(SignatureAlgorithm.ES512,"secret")
                    .compact()

                val cookie = Cookie("jwt", jwt)
                cookie.isHttpOnly=true

                response.addCookie(cookie)

                return ResponseEntity("Successfully LoggedIn",HttpStatus.OK)
            }else{
                throw Warning("Invalid Password")
            }
        }else{
            return ResponseEntity("It is NULL",HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/loggedManager")
    fun loggedManager(@CookieValue("jwt") jwt:String? ):ResponseEntity<Any>{
        try{
            if(jwt == null){
                return ResponseEntity("Unauthenticated",HttpStatus.UNAUTHORIZED)
            }
            val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
            return ResponseEntity(managerServiceImpl.getLoggedManager(body.issuer.toInt()),HttpStatus.OK)
        }catch(e:Exception){
            return ResponseEntity("Unauthorized",HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/logout")
    fun logOutManager(response: HttpServletResponse):ResponseEntity<Any>{
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        return ResponseEntity("Successfully LoggedOut",HttpStatus.OK)

    }





    // employeeAccount operations-------------------------------------

    //These functions are available to the manager after successful login/authentication


    @PostMapping("/addAccount")
    fun addNewAccount(@Valid @RequestBody account: Account): ResponseEntity<Account>{
        var acc = accountServiceImpl.addAccount(account)
        return ResponseEntity(acc, HttpStatus.OK)
    }

    @GetMapping("/getAllAccounts")
    fun getAccounts(): ResponseEntity<MutableList<Account?>>{
        var accList = accountServiceImpl.getAllAccount()
        return ResponseEntity(accList, HttpStatus.OK)
    }

    @GetMapping("/getAccountById/{id}")
    fun getAccountById(@PathVariable id:Int): ResponseEntity<Optional<Account?>> {
        return ResponseEntity(accountServiceImpl.getAccountById(id), HttpStatus.OK)
    }

    @PutMapping("/updateAccountById/{id}")
    fun updateAccountById(@PathVariable id: Int, @Valid @RequestBody account: Account): ResponseEntity<Optional<Account?>> {
        var upAcc = accountServiceImpl.updateAccountById(id,account)
        return ResponseEntity(upAcc, HttpStatus.OK)
    }

    @DeleteMapping("/deleteAccountById/{id}")
    fun deleteAccountById(@PathVariable id: Int): ResponseEntity<String>{
        return ResponseEntity(accountServiceImpl.deleteAccountById(id), HttpStatus.OK)
    }

}