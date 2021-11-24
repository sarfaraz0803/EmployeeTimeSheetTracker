package com.axis.restcrud.service

import com.axis.restcrud.dao.IAccountDao
import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountServiceImpl: IAccountService {

    @Autowired
    private lateinit var iAccountDao: IAccountDao

    override fun addAccount(account: Account): Account {
        if (!iAccountDao.existsById(account._id)) {
            if(!iAccountDao.existsByUsername(account.username)){
                return iAccountDao.save(account)
            }else {
                throw Warning("username already exists")
            }
        } else {
            throw Warning("Id Already Exists")
        }
    }

    override fun getAllAccount(): MutableList<Account?> {
        if(iAccountDao.findAll().isNotEmpty()) {
            return iAccountDao.findAll()
        }else{
            throw Warning("Nothing to show.")
        }
    }

    override fun getAccountById(id: Int): Optional<Account?> {
        if(iAccountDao.existsById(id)){
            return iAccountDao.findById(id)
        }else{
            throw Warning("Id not found.")
        }
    }

    override fun updateAccountById(id: Int, account: Account): Optional<Account?>{
        if(iAccountDao.existsById(id)){
            return  iAccountDao.findById(id).map { oldAcc -> val updateAccount:Account =
                oldAcc.copy(_id = account._id,username = account.username, password = account.password, employee = account.employee)
                iAccountDao.save(updateAccount)
            }
        }else{
            throw Warning("No Account at this Id to update.")
        }
    }

    override fun deleteAccountById(id: Int): String {
        if(iAccountDao.existsById(id)){
            iAccountDao.deleteById(id)
            return "Deleted"
        }else {
            throw Warning("No Account for this Id to delete")
        }
    }
}