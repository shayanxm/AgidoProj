package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToCustomer
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.*
import com.example.mbgjhgjh.services.TransactionService
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.print.attribute.standard.JobOriginatingUserName

@RestController
@RequestMapping("transaction")

class TransactionController(val repository: UserRepo, val loggedInRepo: LoggedInUserRepo) {

    @Autowired
    lateinit var service: TransactionService

    @PostMapping("admin=851851/auszahlen")
    fun reduceAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        service.reduceAmount(request)

    @PostMapping("admin=851851/einzahlen")
    fun increaseAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        service.increaseAmount(request)


    @GetMapping("/admin=851851/all")
    fun getAll(): Messager.TransactionsMessage =
        service.getAll()

    @GetMapping("admin=851851/{userName}")
    fun getUserTransactions(@PathVariable userName: String): java.util.ArrayList<TransactionDb>? =
        service.getUserTransactions(userName)


    @GetMapping("/admin=851851/detail")
    fun getDetail(): ArrayList<TransactionDb> =

        service.getDetail()

    @GetMapping("my_transactions")
    fun getMyTransactions(): java.util.ArrayList<TransactionDb>? =
        service.getUserTransactions(loggedInUserId())


    @PostMapping("payout/{amount}")
    fun payOutMyAccount(@PathVariable amount: Double): TransactionService.TransactionerMessage =
        service.reduceMyAmount(Transaction(loggedInUserId(), amount))

    @PostMapping("payin/{amount}")
    fun payInMyAccount(@PathVariable amount: Double): TransactionService.TransactionerMessage =
        service.increaseMyAmount(Transaction(loggedInUserId(), amount))


    fun loggedInUserId(): String {
        var loggedinUserId = loggedInRepo.findById(1)
        var username = ""
        // loggedInRepo.findAll().forEach { it.userName }
        loggedinUserId.map { username = it.userName }
        print("$username")
        return username
       // Jwts.parser().setSigningKey(Utiles.secretKey).parseClaimsJws(Utiles.loggedinusers).body.issuer
    }

}

