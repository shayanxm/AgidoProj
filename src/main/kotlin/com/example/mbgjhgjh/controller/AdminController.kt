package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Transaction
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.Utiles.aresameDate
import com.example.mbgjhgjh.models.convertToTransactionModel
import com.example.mbgjhgjh.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("secure/")
class Manager(
    val transactionRepo: TransactionRepo,
    val repository: UserRepo,
    @Value("\${example.mbgjhgjh.buildNumber}") val buildNumber: String
) {
    @Autowired
    lateinit var service: TransactionService


    @PostMapping("admin=851851/auszahlen")
    fun reduceAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        service.reduceAmount(request)

    @PostMapping("admin=851851/einzahlen")
    fun increaseAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        service.increaseAmount(request)


    @GetMapping("/admin=851851/all")
    fun getAll(): Messager.TransactionsMessage =
        service.getAll()

    @GetMapping("admin=851851/{userName}")
    fun getUserTransactions(@PathVariable userName: String): java.util.ArrayList<TransactionDb>? =
        service.getUserTransactions(userName)


    @GetMapping("/admin=851851/detail")
    fun getDetail(): ArrayList<TransactionDb> =

        service.getDetail()



    @GetMapping("/version")
    fun getAlXl(): Messager.PlainMessage {
        return Messager.PlainMessage(buildNumber)
    }

//    @GetMapping("/detail")
//    fun getDetail(): ArrayList<TransactionDb> {
//
//        var finalres = ArrayList<TransactionDb>()
//        var res = transactionRepo.findAll().map { it.convertToTransaction() }
//        res.forEach {
//            if (aresameDate(it.date))
//                finalres.add(it.convertToTransactionModel())
//        }
//
//        return finalres
//
//    }

}

