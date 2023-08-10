package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.Utiles.secretKey
import com.example.mbgjhgjh.services.CustomerService
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Date

import java.util.*

@RestController
@RequestMapping("customer")

class CustomerController(val repository: UserRepo, val loggedInUserRepo: LoggedInUserRepo) {


    @Autowired
    lateinit var service: CustomerService

    @PostMapping("/new")
    fun createNewUser(@RequestBody request: Customer): Messager.MessageWithStatus = service.createNewUser(request)


    @PostMapping("admin=851851/edit")
    fun editUser(@RequestBody request: Customer): Messager.MessageWithStatus = service.editUser(request)


    @GetMapping("admin=851851/all")
    fun getAll(): List<Customer> = service.getAllCustomers()


    @GetMapping("admin=851851/count")
    fun counter(): CustomerService.UserCount = service.counter()


    @PostMapping("/login")
    fun login(@RequestBody request: LoginDTO, respone: HttpServletResponse): ResponseEntity<Any> {
        if (loggedInUserRepo.count().toInt()!=0)
            return ResponseEntity.badRequest().body(Messager.PlainMessage("some one is alrady loged in, pls logout first"))

        val user = service.findByUserName(request.userName)
        if (user == null) {
            return ResponseEntity.badRequest().body(Messager.PlainMessage("user not founnd"))
        }
        if (!user.comparePassword(request.password)) {
            return ResponseEntity.badRequest()
                .body(Messager.PlainMessage("invalid password"))
        }


        val issuer = user.userName
        loggedInUserRepo.save(LoggedInUserDb(1,issuer))

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 10000)) // 1 day
            .signWith(SignatureAlgorithm.HS256, secretKey) // Use HS256 with your secret key
            .compact()
        Utiles.loggedinusers = jwt
        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        respone.addCookie(cookie)

        return ResponseEntity.ok(Messager.PlainMessage("successfully logedin, welcome ${user.userName}"))

    }

    @GetMapping("user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {

            if (jwt == null) return ResponseEntity.status(401).body(Messager.PlainMessage("unauthentictied"))

            val body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).body
            return ResponseEntity.ok(this.service.findByUserName(body.issuer))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Messager.PlainMessage("unauthentictied"))
        }

    }

    @PostMapping("logout")
    fun logout(respone: HttpServletResponse): ResponseEntity<Any> {
        if (loggedInUserRepo.count().toInt()==0)
        return ResponseEntity.badRequest().body(Messager.PlainMessage("no one is logged in"))

        var cookie = Cookie("jwt", "value")
        cookie.maxAge = 0
        Utiles.loggedinusers = ""
        loggedInUserRepo.deleteAll()

        respone.addCookie(cookie)
        return ResponseEntity.ok(Messager.PlainMessage("sucessfly logedout"))
    }


}



