package com.example.mbgjhgjh.model

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class Utiles {

    companion object {
        fun aresameDate(givenDate: Date): Boolean {
            return givenDate.toLocalDate() == Date().toLocalDate()
        }

        fun Date.toLocalDate(): LocalDate {
            return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }

    }
}