package com.axis.restcrud.dao

import com.axis.restcrud.modal.Account
import com.axis.restcrud.modal.ManagerAuth
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IAccountDao:MongoRepository<Account, Int> {
    fun existsByUsername(username:String):Boolean
    fun findByUsername(userName:String): Account

}