package com.axis.restcrud.modal

import org.springframework.data.annotation.Id

data class EmployeeTask(
    @Id
    var _id: Int,
    var name: String,
    var status: String
)
