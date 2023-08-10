package com.example.mbgjhgjh.controller.repository.dbmodel

import com.example.mbgjhgjh.models.Transaction
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class TransactionDb(
    val customerId: String,
    val transactionValue: Double,
    var isValidTransaction: Boolean = false,
    val date: Date = Date()
) {

    @Id
    @GeneratedValue
    var id: UUID? = null
        private set
}

 fun TransactionDb.convertToTransaction(): Transaction {
    var transaction = Transaction(
        customerId = this.customerId,
        transactionValue = this.transactionValue,
        date = this.date
    )
    transaction.isValidTransaction = this.isValidTransaction

    transaction.id = this.id

    return transaction
}