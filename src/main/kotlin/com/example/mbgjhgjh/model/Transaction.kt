package com.example.mbgjhgjh.model

import com.example.mbgjhgjh.controller.repository.model.TransactionDb
import java.util.*


data class Transaction(
    val customerId: String,
    var transactionValue: Double,
    val date: Date = Date()
) {
    var status: Boolean = true
    var id: UUID? = null

}

fun Transaction.convertToTransactionModel(): TransactionDb {
    var transactionDb = TransactionDb(
        customerId = this.customerId,
        transactionValue = this.transactionValue,
        status = this.status
    )
    transactionDb.status = this.status
   // transactionDb.id = this.id

    return transactionDb

}


