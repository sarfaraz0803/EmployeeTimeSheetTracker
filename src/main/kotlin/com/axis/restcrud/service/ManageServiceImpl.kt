package com.axis.restcrud.service

import com.axis.restcrud.dao.IAccountDao
import com.axis.restcrud.dao.IEmpCreDao
import com.axis.restcrud.dao.IManagerDao
import com.axis.restcrud.exception.Warning
import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.ManagerAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ManageServiceImpl:IManagerService{

    @Autowired
    private lateinit var iManagerDao: IManagerDao
    @Autowired
    private lateinit var iEmpCreDao: IEmpCreDao
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun registerManager(managerAuth: ManagerAuth): ManagerAuth {

        if(!iManagerDao.existsById(managerAuth._id)){
            if(!iManagerDao.existsByUsername(managerAuth.username)){
                val newManagerAuth = ManagerAuth(managerAuth._id, managerAuth.username, passwordEncoder.encode(managerAuth.password))
                return iManagerDao.save(newManagerAuth)
            }else{
                throw Warning("This Username is Already Exists")
            }
        }else{
            throw Warning("This Id is already In-Use.")
        }
    }

    override fun loginManager(managerAuth: ManagerAuth): ManagerAuth? {
        if(iManagerDao.existsByUsername(managerAuth.username)) {
            return iManagerDao.findByUsername(managerAuth.username)
        }else{
            throw Warning("Profile Not Exists or Check Username Again")
        }
    }

    override fun registerEmployee(empCre: EmpCre): EmpCre {
        if(!iEmpCreDao.existsById(empCre._id)){
            if(!iEmpCreDao.existsByUsername(empCre.username)){
                val empAcc = EmpCre(empCre._id, empCre.username, passwordEncoder.encode(empCre.password))
                return iEmpCreDao.save(empAcc)
            }else{
                throw Warning("Username Already Exists.")
            }
        }else{
            throw Warning("User Already Exists  with this ID.")
        }
    }

    fun getLoggedManager(user:String): ManagerAuth? {
        return iManagerDao.findByUsername(user)
    }

    fun deleteCredentialsDetails(id:Int): String {
        if(iManagerDao.existsById(id)){
            iManagerDao.deleteById(id)
        }else{
            throw Warning("No Credentials To Delete")
        }
        return "Deleted"
    }


}