package com.axis.restcrud.modal


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.text.DateFormat
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class Employee(
    @Id
    var _id: Int,       //employeeID
    var name: String,
    var address:String,
    var age:Int,
    var email:String,
    //var dateOfBirth:String,
   // var mobile:Int
    var department: String,
    var socialCategory:String,
    var physicallyChallenged:Boolean,
    var religion:String,
    var gender:String,
    var maritalStatus:String,
    var profileStatus:String,
    var fatherName:String


    /*
    var profileImage:ByteArray,
    var Aadhaar:Any,
    var signature:Any,
    var thumbImpression
    */


)
