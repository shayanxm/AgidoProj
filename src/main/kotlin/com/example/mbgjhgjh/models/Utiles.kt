package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.dtos.LoginDTO
import io.jsonwebtoken.Jwts
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object Utiles {

    val secretKey = "my_template_secret"
    var loggedinusers: String = ""
    val adminUserName = "admin"
    val adminPassword = "admin"

    fun aresameDate(givenDate: Date): Boolean {
        return givenDate.toLocalDate() == Date().toLocalDate()
    }

    fun Date.toLocalDate(): LocalDate {
        return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }

}