package com.example.mbgjhgjh


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude= [SecurityAutoConfiguration::class])
class MbgjhgjhApplication

fun main(args: Array<String>) {
	runApplication<MbgjhgjhApplication>(*args)

}
