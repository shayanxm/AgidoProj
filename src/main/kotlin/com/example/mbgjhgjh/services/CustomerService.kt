package com.example.mbgjhgjh.services

import com.example.mbgjhgjh.controller.CustomerController
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.convertToDBModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class CustomerService {
    @Autowired
    lateinit var userRepo: UserRepo


    fun getAllCustomers(): List<Customer> {
        return userRepo.findAll().map { it.convertToCustomer() }
    }


    fun createNewUser(customer: Customer): Messager.MessageWithStatus {
        if (!isUniqueUserName(customer.userName))
            return Messager.MessageWithStatus(
                false,
                "username \"${customer.userName}\"  already exists choose something unique"
            )
        if (customer.passWord.length <= 6)
            return Messager.MessageWithStatus(false, "entered password is too short. pls give at least 6 characters")

        var currentCustomer = Customer(customer.userName, customer.passWord)
        currentCustomer.gutHaben = customer.gutHaben
        currentCustomer.firstName = customer.firstName
        currentCustomer.lastName = customer.lastName


        userRepo.save(customer.convertToDBModel())

        return Messager.MessageWithStatus(true, "new user sucessfully created")

    }

    fun counter(): UserCount = UserCount(userRepo.count().toInt())
    data class UserCount(val userCount: Int)

    fun editUser(request: Customer): Messager.MessageWithStatus {
        if (alreadyExistedUserPass(userName = request.userName, password = request.passWord))
            return Messager.MessageWithStatus(false, "entered user and passwrod do not match try again")

        var currentCustomer = Customer(request.userName, request.passWord)
        currentCustomer.gutHaben = request.gutHaben
        currentCustomer.firstName = request.firstName
        currentCustomer.lastName = request.lastName

        userRepo.save(request.convertToDBModel())

        return Messager.MessageWithStatus(true, " user \"${request.userName}\" sucessfully edited")
    }


    fun isUniqueUserName(userName: String): Boolean {
        val allUsers = userRepo.findAll().map { it.convertToCustomer() }
        var foundedUser = allUsers.find { userName == it.userName }
        if (foundedUser != null) {
            return false
        }
        return true
    }

    fun alreadyExistedUserPass(userName: String, password: String): Boolean {
        val allUsers = userRepo.findAll().map { it.convertToCustomer() }
        var foundedUser = allUsers.find { password == it.passWord && userName == it.userName }
        if (foundedUser != null) {
            return false
        }
        return true
    }
}