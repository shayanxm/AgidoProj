package com.example.mbgjhgjh.controller.repository.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class TransactionDb(
    val customerId: String,
    val transactionValue: Double,
    var status: Boolean = false
) {


    @Id
    @GeneratedValue
    var id: UUID? = null
        private set
    val date: Date = Date()
}
