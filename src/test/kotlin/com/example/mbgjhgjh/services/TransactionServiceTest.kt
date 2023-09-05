package com.example.mbgjhgjh.services

import org.junit.jupiter.api.Assertions.*

import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.*
import com.example.mbgjhgjh.services.TransactionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@SpringBootTest
class TransactionServiceTest {

    private lateinit var transactionService: TransactionService

    private val transactionRepo = mockk<TransactionRepo>()
    private val userRepo = mockk<UserRepo>()

    @BeforeEach
    fun setUp() {
        transactionService = TransactionService(transactionRepo, userRepo)
    }

    @Test
    fun `should reduce Amount sucessfully`() {
        // Arrange
        val request = Transaction("userId", 50.0)


        val user = Customer(
            userName = "testuser"
        )
        user.password = "testpassword"
        user.gutHaben = 100.0
        user.firstName = "John"
        user.lastName = "Doe"

        every { userRepo.findById("userId") } returns Optional.of(user.convertToDBModel())
      //  every { transactionRepo.save(any()) } returns slot()

        // Act
        val result = transactionService.reduceAmount(request)

        // Assert
        assertThat(result.successful).isTrue()
        assertThat(result.message).isEqualTo("Transaction is successfully done! New balance for this user= 50.0")
        assertThat(result.newBalance).isEqualTo(50.0)
        assertThat(slot<TransactionDb>().captured.customerId).isEqualTo("userId")
        assertThat(slot<TransactionDb>().captured.transactionValue).isEqualTo(-50.0)
    }

    @Test
    fun `shoul not do transaction due low currency`() {
        // Arrange
        val request = Transaction("userId", 150.0)

        val user = Customer(
            userName = "testuser"
        )
        user.password = "testpassword"
        user.gutHaben = 100.0
        user.firstName = "John"
        user.lastName = "Doe"


        every { userRepo.findById("userId") } returns Optional.of(user.convertToDBModel())

        // Act
        val result = transactionService.reduceAmount(request)

        // Assert
        assertThat(result.successful).isFalse()
        assertThat(result.message).isEqualTo("Transaction failed! Insufficient balance. User needs -100.0 more to complete this Transaction.")
        assertThat(result.newBalance).isEqualTo(100.0)
    }

    @Test
    fun `should fail when no user found`() {
        // Arrange
        val request = Transaction("userId", 50.0)

        every { userRepo.findById("userId") } returns Optional.empty()

        // Act
        val result = transactionService.reduceAmount(request)

        // Assert
        assertThat(result.successful).isFalse()
        assertThat(result.message).isEqualTo("Transaction failed! No user with customerId:userId found or insufficient balance.")
        assertThat(result.newBalance).isEqualTo(0.0)
    }


}