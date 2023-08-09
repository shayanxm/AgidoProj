package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.Repository
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.model.TransactionDb
import com.example.mbgjhgjh.controller.repository.model.convertToCustomer
import com.example.mbgjhgjh.controller.repository.model.convertToTransaction
import com.example.mbgjhgjh.model.Customer
import com.example.mbgjhgjh.model.Transaction
import com.example.mbgjhgjh.model.convertToDBModel
import com.example.mbgjhgjh.model.convertToTransactionModel
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RestController
@RequestMapping("transaction")

class TransactionController(val repository: Repository, val transactionRepo: TransactionRepo) {
    @PostMapping("auszahlen")
    fun reduceAmount(@RequestBody request: Transaction): String {


        return transactioner(request, false)

    }

    @PostMapping("einzahlen")
    fun increaseAmount(@RequestBody request: Transaction): String {

        var transaction = Transaction(request.customerId, -request.transactionValue)

        var currentUser = repository.findById(request.customerId)
        // transaction.susessfullTransaction()


        return transactioner(request, true)

    }


    @GetMapping("/all")
    fun getAll(): String {

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


        return "today we had ${transaktionAnzahl}sucessfull Transactions \n" +
                "we had ${failedTransactions} failed transactions\n" +
                "total payin :  ${payIn}\n" +
                "total payout ${payOut}\n" +
                "and total amount${gesamtBetrag} amount "


    }

    @GetMapping("/detail")
    fun getDetail(): ArrayList<TransactionDb> {

        var finalres = ArrayList<TransactionDb>()
        var res = transactionRepo.findAll().map { it }

        res.forEach {
            if (aresameDate(it.date))
                finalres.add(it)
        }

        return finalres

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


    fun transactioner(request: Transaction, istEinzahlen: Boolean): String {
        var finalRes = ""
        var failed = false
        repository.findById(request.customerId).map { customer ->
            val updatedCustoemr: Customer = customer.convertToCustomer()

            if (istEinzahlen)
                updatedCustoemr.gutHaben += request.transactionValue
            else
                updatedCustoemr.gutHaben -= request.transactionValue

            if (updatedCustoemr.gutHaben > 0) {

                finalRes = "Transaction is sucessfuly done! new balance for this user=  ${updatedCustoemr.gutHaben} "
                repository.delete(customer)
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
                "Transaction failed! no user with username:${request.customerId} found!"
            failed = true
        }
        return finalRes

    }

}


