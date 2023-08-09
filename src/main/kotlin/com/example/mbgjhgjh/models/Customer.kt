package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel


data class Customer(
    var userName: String,
    var passWord: String,
) {
    var firstName: String = ""
    var lastName: String = ""
    var gutHaben: Double = 0.0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (userName != other.userName) return false

        return true
    }

    override fun hashCode(): Int {
        return userName.hashCode()
    }


}

public fun Customer.convertToDBModel() = DBModel(
    userName = this.userName,
    gutHaben = this.gutHaben,
    passWord = this.passWord,
    firstName = this.firstName,
    lastName = this.lastName
)

