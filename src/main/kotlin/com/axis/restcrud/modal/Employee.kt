package com.axis.restcrud.modal


import org.springframework.data.annotation.Id

data class Employee(
    @Id
    var _id: Int = 0,       //employeeID
    var name: String? = "",
    var address:String? = "",
    var age:Int = 0,
    var email:String? = "",
    var mobile:Int = 0,
    var gender:String? = "",
    var department: String? = "",
    var socialCategory:String? = "",
    var physicallyChallenged:Boolean = false,
    var religion:String? = "",
    var dateOfBirth:String? = "",
    var maritalStatus:String? = "",
    var profileStatus:String? = "",
    var fatherName:String? = ""


    /*
    var profileImage:ByteArray,
    var Aadhaar:Any,
    var signature:Any,
    var thumbImpression
    */


)
