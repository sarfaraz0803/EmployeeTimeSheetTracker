package com.axis.restcrud.service

import com.axis.restcrud.modal.Account
import java.util.*

interface IAccountService {

    fun addAccount(account:Account): Account
    fun getAllAccount(): MutableList<Account?>
    fun getAccountById(id:Int): Optional<Account?>
    fun updateAccountById(id: Int, account: Account): Optional<Account?>
    fun deleteAccountById(id:Int): String
}