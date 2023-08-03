package com.example.mbgjhgjh.model

import com.example.mbgjhgjh.controller.repository.model.DBModel


data class Customer(
    var userName: String = "",
    var passWord: String = "",
) {
    var firstName: String = ""
    var lastName: String = ""
    public var gutHaben: Double = 0.0



}
public fun Customer.convertToDBModel() = DBModel(
    userName = this.userName,
    gutHaben = this.gutHaben,
    passWord = this.passWord,
    firstName = this.firstName,
    lastName = this.lastName
)

