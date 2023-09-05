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
@RequestMapping("api/transaction")
class TransactionController(
    private val repository: UserRepo,
    private val loggedInRepo: LoggedInUserRepo,
    private val service: TransactionService
) {

    @GetMapping("my_transactions")
    fun getMyTransactions(): List<TransactionDb>? =
        service.getUserTransactions(loggedInUserId())

    @PostMapping("payout/{amount}")
    fun payOutMyAccount(@PathVariable amount: Double): TransactionService.TransactionerMessage =
        service.reduceMyAmount(Transaction(loggedInUserId(), amount))

    @PostMapping("payin/{amount}")
    fun payInMyAccount(@PathVariable amount: Double): TransactionService.TransactionerMessage =
        service.increaseMyAmount(Transaction(loggedInUserId(), amount))

    private fun loggedInUserId(): String {
        val loggedinUser = loggedInRepo.findById(1)
        return loggedinUser.map { it.userName }.orElse("")
    }
}
