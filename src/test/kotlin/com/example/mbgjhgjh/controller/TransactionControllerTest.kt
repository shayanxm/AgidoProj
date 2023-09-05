package com.example.mbgjhgjh.controller

import org.junit.jupiter.api.Assertions.*

import com.example.mbgjhgjh.controller.repository.LoggedInUserRepo
import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.LoggedInUserDb
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.models.Transaction
import com.example.mbgjhgjh.services.TransactionService
import org.hibernate.internal.util.collections.CollectionHelper.arrayList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringJUnitConfig
@WebMvcTest(TransactionController::class)
class TransactionControllerTest {

    @MockBean
    lateinit var userRepo: UserRepo

    @MockBean
    lateinit var loggedInUserRepo: LoggedInUserRepo
    @MockBean
    lateinit var transacitonRepo: TransactionRepo
    @MockBean
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        // Setup any necessary mock behavior or data before each test
    }

//    @Test
//    fun testGetMyTransactions() {
//        // Mock the behavior of service.getUserTransactions(loggedInUserId())
//        val transactions = listOf(Transaction("user1", 50.0), Transaction("user1", -30.0))
//        val arrayList = ArrayList(transactions)
//        Mockito.`when`(transactionService.getUserTransactions("user1")).thenReturn(transacitonRepo)
//
//        // Perform the GET request
//        mockMvc.perform(
//            MockMvcRequestBuilders.get("/api/transaction/my_transactions")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().json("[{\"customerId\":\"user1\",\"amount\":50.0},{\"customerId\":\"user1\",\"amount\":-30.0}]"))
//    }

    @Test
    fun testPayOutMyAccount() {
        // Mock the behavior of service.reduceMyAmount(transaction)
        val transaction = Transaction("user1", 50.0)
        val transactionerMessage = TransactionService.TransactionerMessage(
            true,
            "Transaction is successfully done! new balance for this user=  50.0 ",
            50.0
        )
        Mockito.`when`(transactionService.reduceMyAmount(transaction)).thenReturn(transactionerMessage)

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/payout/50.0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"success\":true,\"message\":\"Transaction is successfully done! new balance for this user=  50.0 \",\"newBalance\":50.0}"))
    }

    @Test
    fun testPayInMyAccount() {
        // Mock the behavior of service.increaseMyAmount(transaction)
        val transaction = Transaction("user1", 50.0)
        val transactionerMessage = TransactionService.TransactionerMessage(
            true,
            "Transaction is successfully done! new balance for this user=  50.0 ",
            50.0
        )
        Mockito.`when`(transactionService.increaseMyAmount(transaction)).thenReturn(transactionerMessage)

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/payin/50.0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"success\":true,\"message\":\"Transaction is successfully done! new balance for this user=  50.0 \",\"newBalance\":50.0}"))
    }

    // Add more test methods as needed for other controller endpoints
}