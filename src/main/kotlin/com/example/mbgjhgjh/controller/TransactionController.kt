package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.*
import com.example.mbgjhgjh.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.print.attribute.standard.JobOriginatingUserName

@RestController
@RequestMapping("transaction")

class TransactionController(val repository: UserRepo, val transactionRepo: TransactionRepo) {

    @Autowired
    lateinit var service: TransactionService

    @PostMapping("auszahlen")
    fun reduceAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        service.reduceAmount(request)

    @PostMapping("einzahlen")
    fun increaseAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        service.increaseAmount(request)


    @GetMapping("/all")
    fun getAll(): Messager.TransactionsMessage =
        service.getAll()

    @GetMapping("user/{userName}")
    fun getUserTransactions(@PathVariable userName: String): java.util.ArrayList<TransactionDb>? =
        service.getUserTransactions(userName)


    @GetMapping("/detail")
    fun getDetail(): ArrayList<TransactionDb> =

        service.getDetail()


}


