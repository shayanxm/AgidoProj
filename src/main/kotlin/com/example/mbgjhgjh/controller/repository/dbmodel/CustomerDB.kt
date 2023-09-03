package com.example.mbgjhgjh.controller.repository.dbmodel

import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.apache.catalina.User
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


fun DBModel.convertToUserDto(): UserDto {
    var userDto = UserDto()
    userDto.userName=this.userName
    userDto.gutHaben = this.gutHaben
    userDto.firstName = this.firstName
    userDto.lastName = this.lastName
    return userDto
}