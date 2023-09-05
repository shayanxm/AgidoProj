package com.example.mbgjhgjh.dtos

import com.example.mbgjhgjh.controller.repository.dbmodel.DBModel
import com.example.mbgjhgjh.models.Customer
import jakarta.persistence.Column
import jakarta.persistence.Id
import org.apache.catalina.User

class UserDto {

    var userName: String = ""


    var firstName: String = ""


    var lastName: String = ""


    var gutHaben: Double = 0.0
}
