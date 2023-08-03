package com.example.mbgjhgjh.controller.repository.model

import com.example.mbgjhgjh.model.Transaction
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class TransactionDb(
    val customerId: String,
    val transactionValue: Double,
    var status: Boolean = false,
    val date: Date = Date()
) {

    @Id
    @GeneratedValue
    var id: UUID? = null
        private set

}
public fun TransactionDb.convertToTransaction() = TransactionDb(
    customerId = this.customerId,
    transactionValue = this.transactionValue,
    status = this.status,
    date=this.date

    )