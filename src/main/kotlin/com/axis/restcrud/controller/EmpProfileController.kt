package com.axis.restcrud.controller

import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.Account
import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.ManagerAuth
import com.axis.restcrud.service.AccountServiceImpl
import com.axis.restcrud.service.EmpCreServiceImpl
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
@RequestMapping("/employeeProfile")
class EmpProfileController {

    @Autowired
    private lateinit var accountServiceImpl: AccountServiceImpl
    @Autowired
    private lateinit var empCreServiceImpl: EmpCreServiceImpl
    private val passwordEncoder = BCryptPasswordEncoder()
    private lateinit var appSecret:String



    @PutMapping("/updateAccount")
    fun updateAccountById(@CookieValue("empCookie") jwtEmp:String?, @Valid @RequestBody account: Account): ResponseEntity<Any> {
        try{
            if(jwtEmp != null){
                val body = Jwts.parser().setSigningKey(appSecret).parseClaimsJws(jwtEmp).body
                val fetchedAcc = accountServiceImpl.getLoggedEmployee(body.issuer)
                var upAcc = accountServiceImpl.updateAccountById(fetchedAcc._id, account)
                return ResponseEntity(upAcc, HttpStatus.OK)
            }else{
                return ResponseEntity("LogIn First",HttpStatus.UNAUTHORIZED)
            }
        }catch(e:Exception){
            return ResponseEntity(e.toString(),HttpStatus.UNAUTHORIZED)
        }

    }


    @PostMapping("/login")
    fun empAuthentication(@RequestBody empCre: EmpCre, response : HttpServletResponse): ResponseEntity<Any>{
        val empLog = this.empCreServiceImpl.empLogIn(empCre)
        if(empLog != null){
            if(passwordEncoder.matches(empCre.password,empLog.password)){

                val issuer = empLog.username
                appSecret = empLog.username
                val jwtEmp = Jwts.builder()
                    .setIssuer(issuer)
                    .setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 1000))   //  1 hour
                    .signWith(SignatureAlgorithm.HS512, appSecret)
                    .compact()

                val cookie = Cookie("empCookie", jwtEmp)
                cookie.isHttpOnly = true
                //cookie.secure = true

                response.addCookie(cookie)

                return ResponseEntity("Successfully LoggedIn",HttpStatus.OK)
            }else{
                throw Warning("Invalid Password")
            }
        }else{
            return ResponseEntity("Employee is NULL",HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("Please Give Credentials",HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/loggedEmployee")
    fun loggedEmployee(@CookieValue("empCookie") jwtEmp:String? ):ResponseEntity<Any>{
        try{
            if(jwtEmp == null){
                return ResponseEntity("No Logged Employee",HttpStatus.UNAUTHORIZED)
            }
            val body = Jwts.parser().setSigningKey(appSecret).parseClaimsJws(jwtEmp).body
            return ResponseEntity(accountServiceImpl.getLoggedEmployee(body.issuer),HttpStatus.OK)
        }catch(e:Exception){
            return ResponseEntity(e,HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/logout")
    fun logOutManager(@CookieValue("empCookie") jwtEmp:String?, response: HttpServletResponse):ResponseEntity<Any>{
        if(jwtEmp != null){
            val cookie = Cookie("empCookie", jwtEmp)
            cookie.maxAge = 0
            response.addCookie(cookie)
        }else {
            return ResponseEntity("LogIn first", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("Successfully LoggedOut", HttpStatus.OK)

    }


}