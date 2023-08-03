package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.model.DBModel
import com.example.mbgjhgjh.controller.repository.model.TransactionDb
import org.springframework.data.repository.CrudRepository

interface TransactionRepo:CrudRepository<TransactionDb,String>{
}
