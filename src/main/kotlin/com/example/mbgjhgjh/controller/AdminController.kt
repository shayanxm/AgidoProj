package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.*
import com.example.mbgjhgjh.models.Utiles.aresameDate
import com.example.mbgjhgjh.services.CustomerService
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
@RequestMapping("api/secure/")
class AdminController(
    private val service: TransactionService,
    private val customerService: CustomerService,
    private val loggedInUserRepo: LoggedInUserRepo,
    @Value("\${example.mbgjhgjh.buildNumber}") val buildNumber: String
) {

    @GetMapping("allusers")
    fun getAllUsers(): List<UserDto> =
        if (isAdminLoggedIn()) customerService.getAllCustomers() else emptyList()

    @GetMapping("count")
    fun counter(): CustomerService.UserCount = customerService.counter()

    @PostMapping("edit")
    fun editUser(@RequestBody request: Customer): Messager.MessageWithStatus =
        customerService.editUser(request)

    @PostMapping("/cashout")
    fun reduceAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        if (isAdminLoggedIn()) service.reduceAmount(request)
        else TransactionService.TransactionerMessage(false, "access denied", 0.0)

    @PostMapping("/payin")
    fun increaseAmount(@RequestBody request: Transaction): TransactionService.TransactionerMessage =
        if (isAdminLoggedIn()) service.increaseAmount(request)
        else TransactionService.TransactionerMessage(false, "access denied", 0.0)

    @GetMapping("/all")
    fun getAll(): TransactionService.TransactionsMessage =
        if (isAdminLoggedIn()) service.getAll()
        else TransactionService.TransactionsMessage(0, 0, 0.0, 0.0, 0.0)

    @GetMapping("user_transactions/{userName}")
    fun getUserTransactions(@PathVariable userName: String): List<TransactionDb> =
        if (isAdminLoggedIn()) service.getUserTransactions(userName) ?: emptyList()
        else emptyList()

    @GetMapping("/transactions/today")
    fun getDetail(): List<TransactionDb> =
        if (isAdminLoggedIn()) service.getDetail()
        else emptyList()

    @PostMapping("/login")
    fun loginAsAdmin(@RequestBody request: LoginDTO): ResponseEntity<Any> {
        if (loggedInUserRepo.count().toInt() != 0)
            return ResponseEntity.badRequest()
                .body(Messager.PlainMessage("Someone is already logged in, please logout first"))

        if (request.userName == "admin" && request.password == "admin") {
            loggedInUserRepo.save(LoggedInUserDb(2, "Admin"))
            return ResponseEntity.ok(Messager.PlainMessage("Successfully logged in as Admin"))
        }

        return ResponseEntity.badRequest()
            .body(Messager.PlainMessage("Invalid combination of username and password!"))
    }

    @PostMapping("logout")
    fun logoutAsAdmin(): ResponseEntity<Any> {
        if (loggedInUserRepo.count().toInt() == 0)
            return ResponseEntity.badRequest().body(Messager.PlainMessage("No one is logged in"))

        loggedInUserRepo.deleteAll()
        return ResponseEntity.ok(Messager.PlainMessage("Admin successfully logged out"))
    }

    private fun isAdminLoggedIn(): Boolean {
        if (loggedInUserRepo.count().toInt() == 0) return false
        val loggedInUser = loggedInUserRepo.findById(2).orElse(null)
        return loggedInUser?.userName == Utiles.adminUserName
    }
}
