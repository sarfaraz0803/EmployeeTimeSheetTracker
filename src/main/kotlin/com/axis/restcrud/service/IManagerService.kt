package com.axis.restcrud.service

import com.axis.restcrud.modal.EmpCre
import com.axis.restcrud.modal.ManagerAuth

interface IManagerService {

    fun registerManager(managerAuth: ManagerAuth):ManagerAuth
    fun loginManager(managerAuth: ManagerAuth): ManagerAuth?
    fun registerEmployee(empCre: EmpCre): EmpCre

    // Employee operation
   /* fun addEmployee()
    fun viewByEmpId()
    fun viewAllEmployee()
    fun updateEmployee()
    fun deleteEmployee()*/
}