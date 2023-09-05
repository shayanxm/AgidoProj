package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.dtos.LoginDTO
import com.example.mbgjhgjh.dtos.UserDto
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.Transaction
import com.example.mbgjhgjh.models.Utiles
import com.example.mbgjhgjh.services.CustomerService
import com.example.mbgjhgjh.services.TransactionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@WebMvcTest(AdminController::class)
class AdminControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var transactionService: TransactionService

    @MockBean
    private lateinit var customerService: CustomerService

    @MockBean
    private lateinit var loggedInUserRepo: LoggedInUserRepo

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetAllUsers() {
        var user1 = UserDto()
        var user2 = UserDto()
        user1.userName = "user1"
        user2.userName = "user2"
        val userDtoList = listOf(user1, user2)

        `when`(customerService.getAllCustomers()).thenReturn(userDtoList)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure/allusers"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[{\"userName\":\"user1\"},{\"userName\":\"user2\"}]"))
    }

    @Test
    fun testCounter() {
        val userCount = CustomerService.UserCount(10)

        `when`(customerService.counter()).thenReturn(userCount)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure/count"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"userCount\":10}"))
    }

    @Test
    fun testEditUser() {
        // val customer = Customer("testUser", "password", "John", "Doe", 100.0)
        val customer = Customer(
            userName = "testuser"
        )
        customer.password = "testpassword"
        customer.gutHaben = 100.0
        customer.firstName = "John"
        customer.lastName = "Doe"


        val successMessage = Messager.MessageWithStatus(true, "User edited successfully")

        `when`(customerService.editUser(customer)).thenReturn(successMessage)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/secure/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customer.toJson())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json("{\"status\":true,\"message\":\"User edited successfully\"}")
            )
    }

    @Test
    fun testReduceAmount() {
        val transaction = Transaction("testUser", 50.0)
        val transactionMessage = TransactionService.TransactionerMessage(true, "Transaction successful", 50.0)

        `when`(transactionService.reduceAmount(transaction)).thenReturn(transactionMessage)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/secure/cashout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transaction.toJson())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("{\"successful\":true,\"message\":\"Transaction successful\",\"newBalance\":50.0}")
            )
    }

    @Test
    fun testIncreaseAmount() {
        val transaction = Transaction("testUser", 50.0)
        val transactionMessage = TransactionService.TransactionerMessage(true, "Transaction successful", 150.0)

        `when`(transactionService.increaseAmount(transaction)).thenReturn(transactionMessage)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/secure/payin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transaction.toJson())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("{\"successful\":true,\"message\":\"Transaction successful\",\"newBalance\":150.0}")
            )
    }

    @Test
    fun testGetAll() {
        val transactionsMessage = TransactionService.TransactionsMessage(10, 2, 500.0, 100.0, 400.0)

        `when`(transactionService.getAll()).thenReturn(transactionsMessage)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure/all"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("{\"todaySuccessfulTransactions\":10,\"failedTransactions\":2,\"totalPayIn\":500.0,\"totalPayOut\":100.0,\"totalSuccessfulTransactions\":400.0}")
            )
    }

    @Test
    fun testGetUserTransactions() {
        val transactionList =
            listOf(TransactionDb("user1", 50.0, true, Date()), TransactionDb("user2", 100.0, true, Date()))

        `when`(transactionService.getUserTransactions("testUser")).thenReturn(transactionList)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure/user_transactions/testUser"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("[{\"customerId\":\"user1\",\"transactionValue\":50.0},{\"customerId\":\"user2\",\"transactionValue\":100.0}]")
            )
    }

    @Test
    fun testGetDetail() {
        val transactionList =
            listOf(TransactionDb("user1", 50.0, true, Date()), TransactionDb("user2", 100.0, true, Date()))

        `when`(transactionService.getDetail()).thenReturn(transactionList)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/secure/transactions/today"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .json("[{\"customerId\":\"user1\",\"transactionValue\":50.0},{\"customerId\":\"user2\",\"transactionValue\":100.0}]")
            )
    }

    @Test
    fun testLoginAsAdmin() {
        val loginDTO = LoginDTO()
        loginDTO.password = Utiles.adminPassword
        loginDTO.userName = Utiles.adminUserName
        val successMessage = Messager.PlainMessage("Successfully logged in as Admin")

        `when`(loggedInUserRepo.count()).thenReturn(0)
        `when`(loggedInUserRepo.save(LoggedInUserDb(2, "Admin"))).thenReturn(LoggedInUserDb(2, "Admin"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/secure/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDTO.toJson())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Successfully logged in as Admin\"}"))
    }

    @Test
    fun testLogoutAsAdmin() {
        val successMessage = Messager.PlainMessage("Admin successfully logged out")

        `when`(loggedInUserRepo.count()).thenReturn(1)
        `when`(loggedInUserRepo.deleteAll()).then { }

        mockMvc.perform(MockMvcRequestBuilders.post("/api/secure/logout"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Admin successfully logged out\"}"))
    }

    // Add more test cases for your other controller methods as needed

    private inline fun <reified T> T.toJson(): String = ObjectMapper().writeValueAsString(this)
}