package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import org.springframework.data.repository.CrudRepository

interface LoggedInUserRepo : CrudRepository<LoggedInUserDb, Int> {
}