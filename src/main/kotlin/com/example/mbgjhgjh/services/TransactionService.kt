package com.example.mbgjhgjh.services

import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class TransactionService(
    private val transactionRepo: TransactionRepo,
    private val userRepository: UserRepo
) {

    fun reduceAmount(request: Transaction): TransactionerMessage =
        transactioner(request, false)

    fun increaseAmount(request: Transaction): TransactionerMessage =
        transactioner(request, true)

    fun reduceMyAmount(request: Transaction): TransactionerMessage =
        transactioner(request, false)

    fun increaseMyAmount(request: Transaction): TransactionerMessage =
        transactioner(request, true)

    fun getUserTransactions(userName: String): List<TransactionDb>? =
        transactionRepo.findAllByCustomerId(userName)

    fun getAll(): TransactionsMessage {
        val transactions = transactionRepo.findAll().map { it.convertToTransaction() }
        val todayTransactions = transactions.filter { aresameDate(it.date) }

        val (payIn, payOut) = todayTransactions.partition { it.transactionValue > 0 }
        val totalSucessTransactions = todayTransactions.filter { it.isValidTransaction }
            .sumOf { it.transactionValue }

        return TransactionsMessage(
            todayTransactions.size,
            todayTransactions.count { !it.isValidTransaction },
            payIn.sumOf { it.transactionValue },
            payOut.sumOf { -it.transactionValue },
            totalSucessTransactions
        )
    }

    fun getDetail(): List<TransactionDb> =
        transactionRepo.findAll().filter { aresameDate(it.date) }

    private fun transactioner(request: Transaction, isPayIn: Boolean): TransactionerMessage {
        val customer = userRepository.findById(request.customerId).orElse(null)?.convertToCustomer()
        if (customer == null || customer.gutHaben < 0) {
            return TransactionerMessage(
                false,
                "Transaction failed! No user with customerId:${request.customerId} found or insufficient balance.",
                0.0
            )
        }

        if (isPayIn) {
            customer.gutHaben += request.transactionValue
        } else {
            if (customer.gutHaben < request.transactionValue) {
                return TransactionerMessage(
                    false,
                    "Transaction failed! Insufficient balance. User needs ${-customer.gutHaben} more to complete this Transaction.",
                    customer.gutHaben
                )
            }
            customer.gutHaben -= request.transactionValue
        }

        userRepository.save(customer.convertToDBModel())
        val finalRes = if (isPayIn) {
            "Transaction is successfully done! New balance for this user= ${customer.gutHaben}"
        } else {
            "Transaction is successfully done! New balance for this user= ${customer.gutHaben}"
        }

        request.isValidTransaction = true
        transactionRepo.save(request.convertToTransactionModel())

        return TransactionerMessage(true, finalRes, customer.gutHaben)
    }

    private fun aresameDate(givenDate: Date): Boolean =
        givenDate.toLocalDate() == LocalDate.now()

    private fun Date.toLocalDate(): LocalDate =
        this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    data class TransactionerMessage(val successful: Boolean, val message: String, val newBalance: Double)

    data class TransactionsMessage(
        val todaySuccessfulTransactions: Int,
        val failedTransactions: Int,
        val totalPayIn: Double,
        val totalPayOut: Double,
        val totalSuccessfulTransactions: Double
    )
}
