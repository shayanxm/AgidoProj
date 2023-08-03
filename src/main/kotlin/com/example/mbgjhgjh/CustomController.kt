package com.example.mbgjhgjh

import org.springframework.aot.hint.TypeReference
import org.springframework.boot.context.properties.bind.Bindable.listOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.swing.text.View

data class ViewUser(val id:Int, val name:String)
data class CreateAccount(val name:String)
//scan all calsses and it knows it needs to write Routes for it
@RestController
@RequestMapping("customers")

class CustomController {
    @GetMapping("get_name")
    fun returnUserName(): String = "shayan Moradi"

    @GetMapping("/all")
    fun getAll(): Iterable<ViewUser> =
    listOf(ViewUser(12,"shayan"))


    @GetMapping("/hi")
    fun sayHi(): String=
        "hi"

    @PostMapping("/m")
    fun create(@RequestBody  request:CreateAccount): ViewUser=
        ViewUser(2,"shayan")

}