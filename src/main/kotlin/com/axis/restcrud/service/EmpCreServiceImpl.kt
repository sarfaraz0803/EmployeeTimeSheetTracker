package com.axis.restcrud.service

import com.axis.restcrud.dao.IAccountDao
import com.axis.restcrud.dao.IEmpCreDao
import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.Account
import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.ManagerAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class EmpCreServiceImpl {

    @Autowired
    private lateinit var iEmpCreDao: IEmpCreDao
    @Autowired
    private lateinit var iAccountDao: IAccountDao

    fun empLogIn(empCre: EmpCre): EmpCre? {
        if(iEmpCreDao.existsByUsername(empCre.username)) {
            return iEmpCreDao.findByUsername(empCre.username)
        }else{
            throw Warning("Profile Not Exists or Check Username Again")
        }
    }

    fun getLoggedEmployee(user:String): EmpCre? {
        return iEmpCreDao.findByUsername(user)
    }

    //fun loggedEmp(){}
}