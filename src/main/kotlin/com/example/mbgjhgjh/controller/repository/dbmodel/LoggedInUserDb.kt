package com.example.mbgjhgjh.controller.repository.dbmodel

import com.example.mbgjhgjh.models.LoggedInUser
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class LoggedInUserDb(
    @Id
    var catch: Int = 1,

    var userName: String
)
fun LoggedInUserDb.toLoggedInUser():LoggedInUser= LoggedInUser(userName)