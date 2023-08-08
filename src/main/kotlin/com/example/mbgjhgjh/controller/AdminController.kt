package com.example.mbgjhgjh.backend

import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.model.TransactionDb
import com.example.mbgjhgjh.controller.repository.model.convertToTransaction
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("manager")
class Manager(
    val transactionRepo: TransactionRepo,
    @Value ("\${example.mbgjhgjh.buildNumber}") val buildNumber:String
) {

    @GetMapping("/version")
    fun getAlXl(): String {
return buildNumber
    }

    @GetMapping("/detail")
    fun getDetail(): ArrayList<TransactionDb> {

        var finalres = ArrayList<TransactionDb>()
        var res = transactionRepo.findAll().map { it.convertToTransaction() }
        res.forEach {
            if (aresameDate(it.date))
                finalres.add(it)
        }



        return finalres


    }



}

    fun aresameDate(givenDate: Date): Boolean {
        if (givenDate.toLocalDate() == Date().toLocalDate()) {
            println("The two dates are the same.")
            return true
        } else {
            println("The two dates are different.")
            return false
        }

    }

    fun Date.toLocalDate(): LocalDate {
        return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
