package com.example.mbgjhgjh.model

import com.example.mbgjhgjh.controller.UserManger
import com.example.mbgjhgjh.controller.repository.model.DBModel
import com.example.mbgjhgjh.controller.repository.model.TransactionDb
import java.util.Date
import java.util.UUID


data class Transaction(
    val customerId: String,
    val transactionValue: Double,
    val date: Date = Date()
) {
    var status: Boolean = false

}

 fun Transaction.convertToTransactionModel() = TransactionDb(
    customerId = this.customerId,
    transactionValue = this.transactionValue,
    status = this.status,

    )
