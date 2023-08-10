package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


data class Customer(

    var userName: String


) {

    var password: String = ""
        //        @JsonIgnore
        get() = field
    var firstName: String = ""
    var lastName: String = ""
    var gutHaben: Double = 0.0
    fun comparePassword(password: String): Boolean {
        println("real:" + password )
        println( this.password)
        println(BCryptPasswordEncoder().matches("112sxssxxx1", this.password))
        return BCryptPasswordEncoder().matches(password, this.password)
    }

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

    fun secureMyPassword(){
        val passwordEncoder = BCryptPasswordEncoder()
        var encryptedPassword=passwordEncoder.encode(this.password)
        this.password= encryptedPassword
    }

}

public fun Customer.convertToDBModel(): DBModel {
    var dbModel = DBModel(this.userName)
    dbModel.password = this.password
    dbModel.firstName = this.firstName
    dbModel.lastName = this.lastName
    dbModel.gutHaben = this.gutHaben
    return dbModel
}

