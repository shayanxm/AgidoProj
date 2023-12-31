package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Transaction
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.Utiles.aresameDate
import com.example.mbgjhgjh.models.convertToTransactionModel
import com.example.mbgjhgjh.services.TransactionService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@RestController
@RequestMapping("secure/")
class Manager(
    val transactionRepo: TransactionRepo,
    val repository: UserRepo,
    val loggedInRepo: LoggedInUserRepo,
    @Value("\${example.mbgjhgjh.buildNumber}") val buildNumber: String
) {
    @Autowired
    lateinit var service: TransactionService


    @PostMapping("/auszahlen")
    fun reduceAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage {

        if (isAdminLoggedIn()) return service.reduceAmount(request)
        else return TransactionService.TransactionerMessage(false, "acess diniied", 0.0)
    }

    @PostMapping("/einzahlen")
    fun increaseAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage {
        if (isAdminLoggedIn()) return service.increaseAmount(request)
        else return TransactionService.TransactionerMessage(false, "acess diniied", 0.0)
    }


    @GetMapping("/all")
    fun getAll(): Messager.TransactionsMessage {
        if (isAdminLoggedIn())
            return service.getAll()
        else return Messager.TransactionsMessage(0, 0, 0.0, 0.0, 0.0)
    }


    @GetMapping("/{userName}")
    fun getUserTransactions(@PathVariable userName: String): java.util.ArrayList<TransactionDb>? {
        if (isAdminLoggedIn())
            return service.getUserTransactions(userName)
        else return java.util.ArrayList()

    }

    @GetMapping("/detail")
    fun getDetail(): ArrayList<TransactionDb> {
        if (isAdminLoggedIn()) return service.getDetail()
        else return java.util.ArrayList()
    }

//    @GetMapping("/version")
//    fun getAlXl(): Messager.PlainMessage {
//        return Messager.PlainMessage(buildNumber)
//    }


    @PostMapping("/login")
    fun loginAsAdmin(@RequestBody request: LoginDTO): ResponseEntity<Any> {
        if (loggedInRepo.count().toInt() != 0)
            return ResponseEntity.badRequest()
                .body(Messager.PlainMessage("some one is alrady loged in, pls logout first"))


        if (request.userName == "admin" && request.password == "admin") {
            loggedInRepo.save(LoggedInUserDb(2, "Admin"))

            //sucess
            return ResponseEntity.badRequest().body(Messager.PlainMessage("sucessfully loged in as Admin"))
        }
        return ResponseEntity.badRequest()
            .body(Messager.PlainMessage("invalid combination of username and password!"))

    }

    @PostMapping("logout")
    fun logoutAsAdmin(): ResponseEntity<Any> {
        if (loggedInRepo.count().toInt() == 0)
            return ResponseEntity.badRequest().body(Messager.PlainMessage("no one is logged in"))

        loggedInRepo.deleteAll()
        return ResponseEntity.ok(Messager.PlainMessage("Admin sucessfly logedout"))
    }

    fun isAdminLoggedIn(): Boolean {
        println("hi")
        if (loggedInRepo.count().toInt() == 0) return false
        var loggedinUserId = loggedInRepo.findById(2)
        var usernamex = ""
        loggedinUserId.map { usernamex = it.userName }
        println(usernamex)
        if (usernamex == "Admin") {
            println("loged innn")
            return true
        }

        return false
    }
}





