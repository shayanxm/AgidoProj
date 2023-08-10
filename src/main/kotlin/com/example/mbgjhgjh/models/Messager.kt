package com.example.mbgjhgjh.models

object Messager {
    data class MessageWithStatus(val sucessfull: Boolean, val message: String)
    data class PlainMessage(val message: String)

    data class TransactionsMessage(
        val TodaySucessfullTransactions: Int, val failedTransactinos: Int, val totalPayIn: Double,
        val totalPayOut: Double, val totalSucessTransactions: Double
    )
}