package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.Utiles.Companion.aresameDate
import com.example.mbgjhgjh.models.convertToTransactionModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("admin")
class Manager(
    val transactionRepo: TransactionRepo,
    @Value("\${example.mbgjhgjh.buildNumber}") val buildNumber: String
) {

    @GetMapping("/version")
    fun getAlXl(): Utiles.PlainMessage {
        return Utiles.PlainMessage(buildNumber)
    }

    @GetMapping("/detail")
    fun getDetail(): ArrayList<TransactionDb> {

        var finalres = ArrayList<TransactionDb>()
        var res = transactionRepo.findAll().map { it.convertToTransaction() }
        res.forEach {
            if (aresameDate(it.date))
                finalres.add(it.convertToTransactionModel())
        }



        return finalres


    }


}

