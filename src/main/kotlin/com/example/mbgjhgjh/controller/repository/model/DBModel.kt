package com.example.mbgjhgjh.controller.repository.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity
class DBModel(
    var userName: String = "",
    var passWord: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gutHaben: Double = 0.0
) {
    @Id
    @GeneratedValue
    var id: UUID? = null
    private set
    val date: Date =Date()
}




