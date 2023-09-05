package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.models.convertToUserDto
import com.example.mbgjhgjh.services.CustomerService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(CustomerController::class)
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var customerService: CustomerService

    @MockBean
    private lateinit var userRepo: UserRepo

    @MockBean
    private lateinit var loggedInUserRepo: LoggedInUserRepo

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create new user`() {
       // val customer = Customer("testUser", "password", "John", "Doe", 100.0)
        val customer = Customer(
            userName = "testuser"
        )
        customer.password = "testpassword"
        customer.gutHaben = 100.0
        customer.firstName = "John"
        customer.lastName = "Doe"

        val successMessage = Messager.MessageWithStatus(true, "User created successfully")

        `when`(customerService.createNewUser(customer)).thenReturn(successMessage)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/customer/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(customer)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"status\":true,\"message\":\"User created successfully\"}"))
    }

    @Test
    fun `should login`() {
        val loginDTO = LoginDTO()
        loginDTO.password= Utiles.adminPassword
        loginDTO.userName= Utiles.adminUserName
       // val loggedInUser = Customer("testUser", "password", "John", "Doe", 100.0)
        val loggedInUser = Customer(
            userName = "testuser"
        )
        loggedInUser.password = "testpassword"
        loggedInUser.gutHaben = 100.0
        loggedInUser.firstName = "John"
        loggedInUser.lastName = "Doe"


        `when`(customerService.findByUserName1(loginDTO.userName)).thenReturn(loggedInUser)

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(loginDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"status\":true,\"message\":\"Successfully logged in, welcome testUser\"}"))
    }

    @Test
    fun `should log out`() {
        val responseEntity = ResponseEntity.ok(Messager.PlainMessage("Successfully logged out"))

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/logout"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Successfully logged out\"}"))
    }

}