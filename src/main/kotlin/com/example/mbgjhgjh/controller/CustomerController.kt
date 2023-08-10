package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.convertToDBModel
import com.example.mbgjhgjh.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("customer")

class CustomerController(val repository: UserRepo) {
    @Autowired
    lateinit var service: CustomerService

    @PostMapping("/new")
    fun createNewUser(@RequestBody request: Customer): Messager.MessageWithStatus = service.createNewUser(request)


    @PostMapping("/edit")
    fun editUser(@RequestBody request: Customer): Messager.MessageWithStatus = service.editUser(request)


    @GetMapping("/all")
    fun getAll(): List<Customer> = service.getAllCustomers()


    @GetMapping("/count")
    fun counter(): CustomerService.UserCount = service.counter()


}



