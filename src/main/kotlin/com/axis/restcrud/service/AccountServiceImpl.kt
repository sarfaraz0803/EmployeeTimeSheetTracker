package com.axis.restcrud.service

import com.axis.restcrud.dao.IAccountDao
import com.axis.restcrud.dao.IEmpCreDao
import com.axis.restcrud.dao.IManagerDao
import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.Account
import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.Employee
import com.axis.restcrud.modal.ManagerAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountServiceImpl: IAccountService {

    @Autowired
    private lateinit var iAccountDao: IAccountDao
    @Autowired
    private lateinit var iEmpCreDao: IEmpCreDao
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun addAccount(account: Account): Account {
        if (!iAccountDao.existsById(account._id)) {
            if(!iAccountDao.existsByUsername(account.username)){
                if(!iEmpCreDao.existsById(account._id)){
                    iEmpCreDao.save(EmpCre(account._id, account.username, passwordEncoder.encode(account.password)))
                    val newEmpAcc = Account(
                        _id = account._id,
                        username = account.username,
                        password = passwordEncoder.encode(account.password),
                        employee = Employee(
                            _id = account._id,
                            name = account.employee.name,
                            address = account.employee.address,
                            age = account.employee.age,
                            email = account.employee.email,
                            mobile = account.employee.mobile,
                            gender = account.employee.gender,
                            department = account.employee.department,
                            socialCategory = account.employee.socialCategory,
                            physicallyChallenged = account.employee.physicallyChallenged,
                            religion = account.employee.religion,
                            dateOfBirth = account.employee.dateOfBirth,
                            maritalStatus = account.employee.maritalStatus,
                            profileStatus = account.employee.profileStatus,
                            fatherName = account.employee.fatherName
                        ))
                        return iAccountDao.save(newEmpAcc)
                }else{
                    throw Warning("Credentials Already Exists.")
                }
            }else{
                throw Warning("Account already exists by this Username")
            }
        }else {
            throw Warning("Account already exists by this ID")
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
           iEmpCreDao.findById(id).map { oldCre ->
               val newCre = oldCre.copy(
                   _id = id,
                   username = oldCre.username,
                   password = passwordEncoder.encode(account.password)
               )
               iEmpCreDao.save(newCre)
           }
                return iAccountDao.findById(id).map { oldAcc ->
                    val updateAccount: Account =
                        oldAcc.copy(
                            _id = id,
                            username = oldAcc.username,
                            password = passwordEncoder.encode(account.password),
                            employee = account.employee.copy(
                                _id = id,
                                name = account.employee.name,
                                address = account.employee.address,
                                age = account.employee.age,
                                email = account.employee.email,
                                mobile = account.employee.mobile,
                                gender = account.employee.gender,
                                department = account.employee.department,
                                socialCategory = account.employee.socialCategory,
                                physicallyChallenged = account.employee.physicallyChallenged,
                                religion = account.employee.religion,
                                dateOfBirth = account.employee.dateOfBirth,
                                maritalStatus = account.employee.maritalStatus,
                                profileStatus = account.employee.profileStatus,
                                fatherName = account.employee.fatherName
                                )
                        )
                    iAccountDao.save(updateAccount)
                }
        }else{
            throw Warning("No Account at this Id to update.")
        }
    }

    override fun deleteAccountById(id: Int): String {
        if(iAccountDao.existsById(id)){
            iAccountDao.deleteById(id)
            iEmpCreDao.deleteById(id)
            return "Deleted"
        }else {
            throw Warning("No Account for this Id to delete")
        }
    }

    fun getLoggedEmployee(user:String): Account {
        return iAccountDao.findByUsername(user)
    }

}