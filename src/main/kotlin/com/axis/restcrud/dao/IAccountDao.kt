package com.axis.restcrud.dao

import com.axis.restcrud.modal.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IAccountDao:MongoRepository<Account, Int> {
    fun existsByUsername(username:String):Boolean

}