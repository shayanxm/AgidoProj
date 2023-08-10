package com.example.mbgjhgjh.controller.repository.dbmodel

import com.example.mbgjhgjh.models.Customer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@Entity

class DBModel(

    @Id
    var userName: String,

    ) {
    @Column
    var firstName: String = ""

    @Column

    var lastName: String = ""

    @Column
    var gutHaben: Double = 0.0

    @Column
    var password = ""
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    //    @Id
//    @GeneratedValue
//    var id: UUID? = null
    //  private set
    val date: Date = Date()

}


fun DBModel.convertToCustomer(): Customer {
    var customer = Customer(this.userName)
    customer.password = this.password
    customer.gutHaben = this.gutHaben
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    return customer
}
