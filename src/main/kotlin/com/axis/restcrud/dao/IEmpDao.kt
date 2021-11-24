package com.axis.restcrud.dao

import com.axis.restcrud.modal.Employee
import org.springframework.data.mongodb.repository.MongoRepository

interface IEmpDao:MongoRepository<Employee, Int> {
}