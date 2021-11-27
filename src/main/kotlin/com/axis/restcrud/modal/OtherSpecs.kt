package com.axis.restcrud.modal



data class OtherSpecs(


    var dateOfJoining:String,
    var dateOfResign:String,
    var GrievanceCell:Any,
    var resignationLetter:Int,
    var wellnessProgram:Int,
    var officePerks:MutableList<Any>,
    var benefits:MutableList<Any>,
    var taskByManager:MutableList<Any>,
    var totalHoursForTask:Int,
    var performanceGraph:Any,
    var employeeTask : MutableList<EmployeeTask>

)
