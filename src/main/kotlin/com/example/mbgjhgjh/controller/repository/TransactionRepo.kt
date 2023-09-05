package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.models.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepo:CrudRepository<TransactionDb,String>{
    fun findAllByCustomerId(userName:String):java.util.ArrayList<TransactionDb>?
}
