package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb

data class LoggedInUser (
    var userName: String)

fun LoggedInUser.toLoggedInUserDb():LoggedInUserDb=LoggedInUserDb(1,userName)