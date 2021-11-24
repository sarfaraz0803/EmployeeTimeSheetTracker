package com.axis.restcrud.service

import com.axis.restcrud.modal.ManagerAuth

interface IManagerService {

    fun registerManager(managerAuth: ManagerAuth):ManagerAuth
    fun loginManager(managerAuth: ManagerAuth): ManagerAuth?
    fun logoutManager(managerAuth: ManagerAuth): String

    // Employee operation
   /* fun addEmployee()
    fun viewByEmpId()
    fun viewAllEmployee()
    fun updateEmployee()
    fun deleteEmployee()*/
}