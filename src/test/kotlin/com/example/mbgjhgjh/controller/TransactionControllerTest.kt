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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`

@WebMvcTest(TransactionController::class)
@ActiveProfiles("test")
class TransactionControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var transactionService: TransactionService

    @MockBean
    private lateinit var userRepo: UserRepo

    @MockBean
    private lateinit var loggedInUserRepo: LoggedInUserRepo

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Should return transactions`() {
        `when`(loggedInUserRepo.findById(1)).thenReturn(Optional.of(LoggedInUserDb(1, "testUser")))
        `when`(transactionService.getUserTransactions("testUser")).thenReturn(null) // You can change this as needed

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transaction/my_transactions")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("null"))
    }

    @Test
    fun `should reduce amount successfly`() {
        `when`(loggedInUserRepo.findById(1)).thenReturn(Optional.of(LoggedInUserDb(1, "testUser")))
        `when`(transactionService.reduceMyAmount(any())).thenReturn(
            TransactionService.TransactionerMessage(
                true,
                "Success",
                100.0
            )
        )

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/payout/100")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"successful\":true,\"message\":\"Success\",\"newBalance\":100.0}"))
    }

    @Test
    fun `should pay in Money`() {
        `when`(loggedInUserRepo.findById(1)).thenReturn(Optional.of(LoggedInUserDb(1, "testUser")))
        `when`(transactionService.increaseMyAmount(any())).thenReturn(
            TransactionService.TransactionerMessage(
                true,
                "Success",
                200.0
            )
        )

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/payin/200")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("{\"successful\":true,\"message\":\"Success\",\"newBalance\":200.0}"))
    }
}