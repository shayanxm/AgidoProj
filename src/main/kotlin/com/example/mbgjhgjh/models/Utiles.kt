package com.example.mbgjhgjh.models

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object Utiles {

    fun aresameDate(givenDate: Date): Boolean {
        return givenDate.toLocalDate() == Date().toLocalDate()
    }

    fun Date.toLocalDate(): LocalDate {
        return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }



}