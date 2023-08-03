package com.example.mbgjhgjh

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("w")
class wtfer {
    @GetMapping("/wtf")
    fun sayHi(): String=
        "wtf"

}