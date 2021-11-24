package com.axis.restcrud.service

import com.axis.restcrud.dao.IManagerDao
import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.ManagerAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ManageServiceImpl:IManagerService{

    @Autowired
    private lateinit var iManagerDao: IManagerDao
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun registerManager(managerAuth: ManagerAuth): ManagerAuth {
        var newManagerAuth = ManagerAuth(managerAuth._id, managerAuth.username, passwordEncoder.encode(managerAuth.password))
        return iManagerDao.save(newManagerAuth)
    }

    override fun loginManager(managerAuth: ManagerAuth): ManagerAuth? {
        if(iManagerDao.existsByUsername(managerAuth.username)) {
            return iManagerDao.findByUsername(managerAuth.username)
        }else{
            throw Warning("Profile Not Exists Check Username Again")
        }

        /*var manLogin = iManagerDao.findByUsername(manager.username)
        if(manLogin != null){
            if(passwordEncoder.matches(manager.password,manLogin.password)){
                return manLogin
            }else{
                throw Warning("Password is Invalid")
            }
        }else{
            throw Warning("Profile Not Exists")
        }*/
    }

    fun getLoggedManager(id:Int): Optional<ManagerAuth> {
        return iManagerDao.findById(id)
    }

    override fun logoutManager(managerAuth: ManagerAuth): String {
        TODO("Not yet implemented")
    }


}