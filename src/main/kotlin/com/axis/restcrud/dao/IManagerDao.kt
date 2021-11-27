package com.axis.restcrud.dao

import com.axis.restcrud.modal.ManagerAuth
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query


interface IManagerDao: MongoRepository<ManagerAuth, Int> {
    fun findByUsername(userName:String):ManagerAuth?
    fun existsByUsername(userName:String):Boolean
    /*@Query(value = "{'_id' : ?0}", fields = "{'password' : 0}")
    fun getUsernameById(id:Int):String*/
}