package com.example.mbgjhgjh.controller.repository.model

import com.example.mbgjhgjh.model.Customer
import com.example.mbgjhgjh.model.convertToDBModel
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity

class DBModel(
@Id
    var userName: String = "",
    var passWord: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gutHaben: Double = 0.0
) {
//    @Id
//    @GeneratedValue
//    var id: UUID? = null
      //  private set
    val date: Date = Date()
}

public fun DBModel.convertToCustomer(): Customer {
    var customer = Customer(this.userName, this.passWord)
    customer.gutHaben = this.gutHaben
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    return customer
}

