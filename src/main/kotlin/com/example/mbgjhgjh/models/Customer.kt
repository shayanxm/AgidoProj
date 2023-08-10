package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.AbstractPasswordEncoder


data class Customer(

    var userName: String


) {
    var passWord: String = ""
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

public fun Customer.convertToDBModel(): DBModel {
    var dbModel = DBModel(this.userName)
    dbModel.password = this.passWord
    dbModel.firstName = this.firstName
    dbModel.lastName = this.lastName
    dbModel.gutHaben = this.gutHaben
    return dbModel
}

