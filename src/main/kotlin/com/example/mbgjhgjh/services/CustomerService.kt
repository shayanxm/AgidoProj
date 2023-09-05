package com.example.mbgjhgjh.services

import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToUserDto
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.convertToDBModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
@Service
class CustomerService(private val userRepo: UserRepo) {

    fun getAllCustomers(): List<UserDto> =
        userRepo.findAll().map { it.convertToUserDto() }

    fun createNewUser(customer: Customer): Messager.MessageWithStatus {
        if (!isUniqueUserName(customer.userName))
            return Messager.MessageWithStatus(
                false,
                "customerId \"${customer.userName}\" already exists, please choose something unique"
            )
        if (customer.password.length < 6)
            return Messager.MessageWithStatus(false, "entered password is too short. Please use at least 6 characters")

        customer.secureMyPassword()
        userRepo.save(customer.convertToDBModel())

        return Messager.MessageWithStatus(true, "new user '${customer.userName}' successfully created")
    }

    fun findByUserName1(username: String): Customer? =
        userRepo.findByUserName(username)?.convertToCustomer()

    fun findByUserName(username: String): UserDto? =
        userRepo.findByUserName(username)?.convertToUserDto()

    fun counter(): UserCount = UserCount(userRepo.count().toInt())

    data class UserCount(val userCount: Int)

    fun editUser(request: Customer): Messager.MessageWithStatus {
        if (alreadyExistedUserPass(userName = request.userName, password = request.password))
            return Messager.MessageWithStatus(false, "entered user and password do not match. Please try again")

        val currentCustomer = Customer(request.userName).apply {
            password = request.password
            gutHaben = request.gutHaben
            firstName = request.firstName
            lastName = request.lastName
        }

        userRepo.save(request.convertToDBModel())

        return Messager.MessageWithStatus(true, "user \"${request.userName}\" successfully edited")
    }

    fun isUniqueUserName(userName: String): Boolean =
        userRepo.findAll().map { it.convertToCustomer() }.none { userName == it.userName }

    fun alreadyExistedUserPass(userName: String, password: String): Boolean =
        userRepo.findAll().map { it.convertToCustomer() }.any { password == it.password && userName == it.userName }
}
