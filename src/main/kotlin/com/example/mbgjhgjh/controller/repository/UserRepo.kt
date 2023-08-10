package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.models.Customer
import org.springframework.data.repository.CrudRepository

interface UserRepo:CrudRepository<DBModel,String> {
    fun findByUserName(customerId:String):DBModel?

}