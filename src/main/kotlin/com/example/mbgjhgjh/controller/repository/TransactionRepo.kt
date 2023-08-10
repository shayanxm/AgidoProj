package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.models.Customer
import org.springframework.data.repository.CrudRepository

interface TransactionRepo:CrudRepository<TransactionDb,String>{
    fun findAllByCustomerId(customerId:String):java.util.ArrayList<TransactionDb>
}
