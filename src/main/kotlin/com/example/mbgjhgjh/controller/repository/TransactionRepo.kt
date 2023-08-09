package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import org.springframework.data.repository.CrudRepository

interface TransactionRepo:CrudRepository<TransactionDb,String>{
}
