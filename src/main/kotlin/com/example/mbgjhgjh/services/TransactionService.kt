package com.example.mbgjhgjh.services

import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

@Service
class TransactionService {

    @Autowired
    lateinit var transactionRepo: TransactionRepo

    @Autowired
    lateinit var repository: UserRepo

    fun reduceAmount(request: Transaction): TransactionerMessage {


        return transactioner(request, false)

    }

    fun increaseAmount(request: Transaction): TransactionerMessage {

        return transactioner(request, true)

    }


    fun reduceMyAmount(request: Transaction): TransactionerMessage {


        return transactioner(request, false)

    }

    fun increaseMyAmount(request: Transaction): TransactionerMessage {

        return transactioner(request, true)

    }


    @GetMapping("user_transactions")
    fun getUserTransactions(userName: String): java.util.ArrayList<TransactionDb>? {
        return transactionRepo.findAllByCustomerId(userName)

    }


    fun getAll(): Messager.TransactionsMessage {

        var finalres = ArrayList<TransactionDb>()
        var res = transactionRepo.findAll().map { it.convertToTransaction() }

        res.find { aresameDate(it.date) }
        res.forEach {
            if (aresameDate(it.date))
                finalres.add(it.convertToTransactionModel())
        }

        var gesamtBetrag = 0.0
        var transaktionAnzahl = 0
        var failedTransactions = 0
        var payIn = 0.0
        var payOut = 0.0
        finalres.forEach { element ->
            if (element.isValidTransaction) {
                if (element.transactionValue > 0) payIn += element.transactionValue
                if (element.transactionValue < 0) payOut -= element.transactionValue

                gesamtBetrag += element.transactionValue
                transaktionAnzahl++
            } else failedTransactions++
        }

        return Messager.TransactionsMessage(
            transaktionAnzahl,
            failedTransactions,
            payIn,
            payOut,
            gesamtBetrag
        )


    }


    fun getDetail(): ArrayList<TransactionDb> {

        var finalres = ArrayList<TransactionDb>()
        var res = transactionRepo.findAll().map { it }

        res.forEach {
            if (aresameDate(it.date))
                finalres.add(it)
        }
        return finalres
    }

    fun transactioner(request: Transaction, istEinzahlen: Boolean): TransactionerMessage {
        var finalRes = ""
        var failed = false
        var newBalance = 0.0
        repository.findById(request.customerId).map { customer ->
            val updatedCustoemr: Customer = customer.convertToCustomer()
            newBalance = updatedCustoemr.gutHaben

            if (istEinzahlen)
                updatedCustoemr.gutHaben += request.transactionValue
            else
                updatedCustoemr.gutHaben -= request.transactionValue

            if (updatedCustoemr.gutHaben > 0) {
                newBalance = updatedCustoemr.gutHaben
                finalRes = "Transaction is sucessfuly done! new balance for this user=  ${updatedCustoemr.gutHaben} "
                repository.delete(customer)
                println("stillpasss" + customer.password + updatedCustoemr.password)
                repository.save(updatedCustoemr.convertToDBModel())
            } else {
                finalRes =
                    "Transaction failed due low curreny.user need  ${-updatedCustoemr.gutHaben} more to do this Transaction"
                failed = true
            }
        }


        if (!istEinzahlen) request.transactionValue = -request.transactionValue
        if (failed) request.isValidTransaction = false
        transactionRepo.save(request.convertToTransactionModel())

        if (repository.findById(request.customerId).isEmpty) {
            finalRes =
                "Transaction failed! no user with customerId:${request.customerId} found!"
            failed = true
        }
        return TransactionerMessage(!failed, finalRes, newBalance)

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

    data class TransactionerMessage(val sucessfull: Boolean, val message: String, val newBalance: Double)

    data class TransactionsMessage(
        val TodaySucessfullTransactions: Int, val failedTransactinos: Int, val totalPayIn: Double,
        val totalPayOut: Double, val totalSucessTransactions: Double
    )
}