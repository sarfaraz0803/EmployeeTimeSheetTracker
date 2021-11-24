package com.axis.restcrud.modal


import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.UniqueElements
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("manager")
data class ManagerAuth (
    @Id
    var _id: Int,
    @field:UniqueElements
    var username: String,
    var password: String
)