package com.example.mbgjhgjh.backend

import com.example.mbgjhgjh.controller.UserManger
import com.example.mbgjhgjh.controller.repository.Repository
import com.example.mbgjhgjh.db.LiveDB
import com.example.mbgjhgjh.model.Customer
import com.example.mbgjhgjh.model.convertToDBModel
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("customer")

class CustomerController(val repository: Repository) {

    @PostMapping("/new")
    fun createNewUser(@RequestBody request: Customer) {

        var currentCustomer = Customer(request.userName, request.passWord)
        currentCustomer.gutHaben = request.gutHaben
        currentCustomer.firstName = request.firstName
        currentCustomer.lastName = request.lastName


        UserManger.createCustomer(inputCustomer = currentCustomer)
        repository.save(request.convertToDBModel())
        println(request.userName)
    }

    @GetMapping("/all")
    fun getAll(): List<ArrayList<Customer>> {

        // if (LiveDB.dabaseSaticObj.listOfCustomers.isNotEmpty())
        return listOf( LiveDB.dabaseSaticObj.listOfCustomers)
        //catch here



    }

    @GetMapping("/hi")
    fun sayHi(): String=
        "hi"


    @GetMapping("/count")
    fun counter(): Int=
        LiveDB.dabaseSaticObj.listOfCustomers.size


}
