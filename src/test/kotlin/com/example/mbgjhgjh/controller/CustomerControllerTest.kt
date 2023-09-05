package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.convertToUserDto
import com.example.mbgjhgjh.services.CustomerService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal class CustomerControllerTest {
    @Mock
    lateinit var repository: UserRepo

    @Mock
    lateinit var loggedInUserRepo: LoggedInUserRepo

    lateinit var customerController: CustomerController
    private val SECRET_KEY = "your-secret-key"

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val customerService = Mockito.mock(CustomerService::class.java)
        customerController = CustomerController(repository, loggedInUserRepo)
        // customerController.secretKey = SECRET_KEY // Inject your secret key
        customerController.service = customerService

        // Optional: You can set up any required dependencies here
    }

    @Test
    fun testCreateNewUser() {
        // Mock the behavior of the service method
        val request = Customer("") // Initialize request
        val expectedResponse = Messager.MessageWithStatus(true, "Created")
        Mockito.`when`(customerController.service.createNewUser(request)).thenReturn(expectedResponse)

        // Perform the test
        val result = customerController.createNewUser(request)

        // Assert the result
        assert(result == expectedResponse)
    }

    @Test
    fun testEditUser() {
        // Mock the behavior of the service method
        val request = Customer("") // Initialize request
        val expectedResponse = Messager.MessageWithStatus(true, "Updated")
        Mockito.`when`(customerController.service.editUser(request)).thenReturn(expectedResponse)

        // Perform the test
        val result = customerController.editUser(request)

        // Assert the result
        assert(result == expectedResponse)
    }

//    @Test
//    fun testGetAll() {
//        // Mock the behavior of the service method
//        val expectedResponse = listOf(UserDto()) // Initialize with expected data
//        Mockito.`when`(customerController.service.getAllCustomers()).thenReturn(expectedResponse)
//
//        // Perform the test
//        val result = customerController.getAll()
//
//        // Assert the result
//        assert(result == expectedResponse)
//    }
//
//    @Test
//    fun testCounter() {
//        // Mock the behavior of the service method
//        val expectedResponse = CustomerService.UserCount(10) // Initialize with expected count
//        Mockito.`when`(customerController.service.counter()).thenReturn(expectedResponse)
//
//        // Perform the test
//        val result = customerController.counter()
//
//        // Assert the result
//        assert(result == expectedResponse)
//    }

    @Test
    fun testLogin() {
        // Mock the behavior of the service method and dependencies
        var request = LoginDTO()
        request.userName = "username"
        request.password = "passwrod"
        val user = Customer("") // Initialize with expected user data
        val expectedJwt = "your-expected-jwt" // Initialize with an expected JWT token
        val response = Mockito.mock(HttpServletResponse::class.java)
        val expectedResponse = ResponseEntity.ok(Messager.PlainMessage("successfully logged in, welcome username"))

        Mockito.`when`(loggedInUserRepo.count()).thenReturn(0)
        Mockito.`when`(customerController.service.findByUserName1(request.userName)).thenReturn(user)
        Mockito.`when`(customerController.service.findByUserName(user.userName)).thenReturn(user.convertToUserDto())
        Mockito.`when`(user.comparePassword( request.password)).thenReturn(true)
        Mockito.`when`(response.addCookie(Mockito.any())).thenAnswer {
            val cookie = it.getArgument<Cookie>(0)
            assert(cookie.name == "jwt")
            assert(cookie.value == expectedJwt)
        }

        // Perform the test
        val result = customerController.login(request, response)

        // Assert the result
        assert(result == expectedResponse)
    }

    @Test
    fun testUser() {
        // Mock the behavior of the method
        val jwt = "your-jwt-token" // Initialize with a valid JWT token
        val expectedUser = Customer("") // Initialize with expected user data
        Mockito.`when`(customerController.service.findByUserName(expectedUser.userName))
            .thenReturn(expectedUser.convertToUserDto())

        // Perform the test
        val result = customerController.user(jwt)

        // Assert the result
        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == expectedUser)
    }

    @Test
    fun testLogout() {
        // Mock the behavior of the method and dependencies
        val response = Mockito.mock(HttpServletResponse::class.java)
        val expectedResponse = ResponseEntity.ok(Messager.PlainMessage("successfully logged out"))

        Mockito.`when`(loggedInUserRepo.count()).thenReturn(1)
        Mockito.`when`(response.addCookie(Mockito.any())).thenAnswer {
            val cookie = it.getArgument<Cookie>(0)
            assert(cookie.name == "jwt")
            assert(cookie.maxAge == 0)
        }

        // Perform the test
        val result = customerController.logout(response)

        // Assert the result
        assert(result == expectedResponse)
    }
}