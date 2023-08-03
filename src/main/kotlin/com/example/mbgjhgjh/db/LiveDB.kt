package com.example.mbgjhgjh.db

import com.example.mbgjhgjh.model.Customer
import com.example.mbgjhgjh.model.Transaction


class LiveDB() {
    companion object {
        @JvmStatic
        lateinit var dabaseSaticObj: LiveDB
    }
    init {
        dabaseSaticObj  = this
    }
    lateinit var currentLogedinCustomer: Customer
    var listOfCustomers: ArrayList<Customer> = ArrayList()

    var listOfTransaction: ArrayList<Transaction> = ArrayList()}


