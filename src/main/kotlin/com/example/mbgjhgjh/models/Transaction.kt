package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import java.util.*


data class Transaction(
    val customerId: String,
    var transactionValue: Double,
    val date: Date = Date()
) {
    var isValidTransaction: Boolean = true
    var id: UUID? = null

}

fun Transaction.convertToTransactionModel(): TransactionDb {
    var transactionDb = TransactionDb(
        customerId = this.customerId,
        transactionValue = this.transactionValue,
        isValidTransaction = this.isValidTransaction
    )
    transactionDb.isValidTransaction = this.isValidTransaction
    // transactionDb.id = this.id

    return transactionDb

}


