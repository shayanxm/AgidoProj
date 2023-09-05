package com.example.mbgjhgjh.controller.repository

import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LoggedInUserRepo : CrudRepository<LoggedInUserDb, Int> {
}