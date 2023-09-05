package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.dtos.UserDto
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
@RequestMapping("api/customer")
class CustomerController(
    private val repository: UserRepo,
    private val loggedInUserRepo: LoggedInUserRepo,
    private val service: CustomerService
) {

    @PostMapping("/new")
    fun createNewUser(@RequestBody request: Customer): Messager.MessageWithStatus =
        service.createNewUser(request)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        if (loggedInUserRepo.count().toInt() != 0)
            return ResponseEntity.badRequest().body(Messager.PlainMessage("Someone is already logged in, please log out first"))

        val user = service.findByUserName1(request.userName)
        if (user == null) {
            return ResponseEntity.badRequest().body(Messager.PlainMessage("User not found"))
        }
        if (!user.comparePassword(request.password)) {
            return ResponseEntity.badRequest()
                .body(Messager.PlainMessage("Invalid password"))
        }

        val issuer = user.userName
        loggedInUserRepo.save(LoggedInUserDb(1, issuer))

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 10000)) // 1 day
            .signWith(SignatureAlgorithm.HS256, Utiles.secretKey) // Use HS256 with your secret key
            .compact()
        Utiles.loggedinusers = jwt
        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok(Messager.PlainMessage("Successfully logged in, welcome ${user.userName}"))
    }

    @GetMapping("loggedin_acc")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null) return ResponseEntity.status(401).body(Messager.PlainMessage("Unauthorized"))

            val body = Jwts.parser().setSigningKey(Utiles.secretKey).parseClaimsJws(jwt).body
            return ResponseEntity.ok(service.findByUserName(body.issuer))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Messager.PlainMessage("Unauthorized"))
        }
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        if (loggedInUserRepo.count().toInt() == 0)
            return ResponseEntity.badRequest().body(Messager.PlainMessage("No one is logged in"))

        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        Utiles.loggedinusers = ""
        loggedInUserRepo.deleteAll()

        response.addCookie(cookie)
        return ResponseEntity.ok(Messager.PlainMessage("Successfully logged out"))
    }
}
