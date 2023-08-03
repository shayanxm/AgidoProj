package com.example.mbgjhgjh


import com.example.mbgjhgjh.db.LiveDB
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MbgjhgjhApplication

fun main(args: Array<String>) {
	runApplication<MbgjhgjhApplication>(*args)
	LiveDB.dabaseSaticObj= LiveDB()

}
