package com.axis.restcrud.controller

import com.axis.restcrud.modal.Account
import com.axis.restcrud.service.AccountServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/employeeProfile")
class EmpProfileController {

    @Autowired
    private lateinit var accountServiceImpl: AccountServiceImpl

    @PutMapping("/updateAccountById/{id}")
    fun updateAccountById(@PathVariable id: Int,@Valid @RequestBody account: Account): ResponseEntity<Optional<Account?>> {
        var upAcc = accountServiceImpl.updateAccountById(id,account)
        return ResponseEntity(upAcc, HttpStatus.OK)
    }

    /*@PostMapping("/addEmployee")
    fun addNewEmployee(@RequestBody empPro : Employee):ResponseEntity<Employee>{
        return ResponseEntity(empProServiceImpl.addEmployeeProfile(empPro),HttpStatus.OK)
    }

    @PutMapping("/updateEmployee/{id}")
    fun updateEmployee(@PathVariable id:Int, @RequestBody empPro:Employee):ResponseEntity<Any>{
        return ResponseEntity(empProServiceImpl.updateEmployeeProfile(id,empPro),HttpStatus.OK)
    }

    @DeleteMapping("/deleteEmployee/{id}")
    fun deleteEmployee(@PathVariable id:Int):ResponseEntity<Any>{
        return ResponseEntity(empProServiceImpl.deleteEmployeeProfile(id),HttpStatus.OK)

    }*/
}