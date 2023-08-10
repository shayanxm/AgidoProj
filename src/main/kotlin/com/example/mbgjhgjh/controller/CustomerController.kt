package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.services.CustomerService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Date

import java.util.*

@RestController
@RequestMapping("customer")

class CustomerController(val repository: UserRepo) {
    @Autowired
    lateinit var service: CustomerService

    @PostMapping("/new")
    fun createNewUser(@RequestBody request: Customer): Messager.MessageWithStatus = service.createNewUser(request)




    @PostMapping("/edit")
    fun editUser(@RequestBody request: Customer): Messager.MessageWithStatus = service.editUser(request)


    @GetMapping("/all")
    fun getAll(): List<Customer> = service.getAllCustomers()


    @GetMapping("/count")
    fun counter(): CustomerService.UserCount = service.counter()


    @PostMapping("/login")
    fun login(@RequestBody request: LoginDTO,respone:HttpServletResponse): ResponseEntity<Any> {
        val user = service.findByUserName(request.userName)
        if (user == null) {
            return ResponseEntity.badRequest().body(Messager.PlainMessage("user not founnd"))
        }
        if (!user.comparePassword(request.password)) {
            return ResponseEntity.badRequest()
                .body(Messager.PlainMessage("invalid password"))
        }

        val secretKey = "my_template_secret"

        val issuer = user.userName

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 10000)) // 1 day
            .signWith(SignatureAlgorithm.HS256, secretKey) // Use HS256 with your secret key
            .compact()
        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly=true
        respone.addCookie(cookie)

        return ResponseEntity.ok(Messager.PlainMessage("Sucesss"))

    }


}



