package com.example.mbgjhgjh.backend

import com.example.mbgjhgjh.controller.UserManger
import com.example.mbgjhgjh.controller.repository.Repository
import com.example.mbgjhgjh.controller.repository.model.convertToCustomer
import com.example.mbgjhgjh.model.Customer
import com.example.mbgjhgjh.model.convertToDBModel
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("customer")

class CustomerController(val repository: Repository) {

    @PostMapping("/new")
    fun createNewUser(@RequestBody request: Customer): String {

        if (!isUniqueUserName(request.userName)) return "username \"${request.userName}\"  already exists choose something unique"
        if (request.passWord.length <= 6) return "entered password is too short. pls give at least 6 characters"

        var currentCustomer = Customer(request.userName, request.passWord)
        currentCustomer.gutHaben = request.gutHaben
        currentCustomer.firstName = request.firstName
        currentCustomer.lastName = request.lastName


        repository.save(request.convertToDBModel())

        return "new user sucessfully created"

    }

    @PostMapping("/edit")
    fun editUser(@RequestBody request: Customer): String {
        if (alreadyExistedUserPass(userName = request.userName, password = request.passWord))
            return "entered user and passwrod do not match try again"


        var currentCustomer = Customer(request.userName, request.passWord)
        currentCustomer.gutHaben = request.gutHaben
        currentCustomer.firstName = request.firstName
        currentCustomer.lastName = request.lastName


        repository.save(request.convertToDBModel())

        return " user \"${request.userName}\" sucessfully edited"

    }

    @GetMapping("/all")
    fun getAll(): List<Customer> {

        return repository.findAll().map { it.convertToCustomer() }

    }


    @GetMapping("/count")
    fun counter(): String =
        "we have ${repository.count().toInt()} users"

    fun isUniqueUserName(userName: String): Boolean {
        val allUsers = repository.findAll().map { it.convertToCustomer() }
        var foundedUser = allUsers.find { userName == it.userName }
        if (foundedUser != null) {
            return false
        }
        return true
    }

    fun alreadyExistedUserPass(userName: String, password: String): Boolean {
        val allUsers = repository.findAll().map { it.convertToCustomer() }
        var foundedUser = allUsers.find { password == it.passWord && userName == it.userName }
        if (foundedUser != null) {
            return false
        }
        return true
    }
}



