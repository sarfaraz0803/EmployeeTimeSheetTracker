package com.axis.restcrud.dao

import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.Employee
import com.axis.restcrud.modal.ManagerAuth
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface IEmpCreDao:MongoRepository<EmpCre, Int> {
    fun findByUsername(userName:String): EmpCre?
    fun existsByUsername(userName:String):Boolean
    @Query(value = "{'_id' : ?0}", fields = "{'password' : 0}")
    fun usernameById(id:Int):String
    @Query(value = "{'_id' : ?0}", fields = "{'username' : 0}")
    fun passwordById(id:Int):String
}