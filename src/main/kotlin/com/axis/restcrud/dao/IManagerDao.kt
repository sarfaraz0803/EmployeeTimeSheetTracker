package com.axis.restcrud.dao

import com.axis.restcrud.modal.ManagerAuth
import org.springframework.data.mongodb.repository.MongoRepository

interface IManagerDao: MongoRepository<ManagerAuth, Int> {
    fun findByUsername(userName:String):ManagerAuth?
    fun existsByUsername(userName:String):Boolean
}