package com.example.mbgjhgjh.controller.repository.dbmodel

import com.example.mbgjhgjh.models.Customer
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity

class DBModel(
    @Id
    var userName: String,

    var passWord: String,
    var firstName: String ,
    var lastName: String ,

    var gutHaben: Double
) {
    //    @Id
//    @GeneratedValue
//    var id: UUID? = null
    //  private set
    val date: Date = Date()
}

fun DBModel.convertToCustomer(): Customer {
    var customer = Customer(this.userName, this.passWord)
    customer.gutHaben = this.gutHaben
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    return customer
}

