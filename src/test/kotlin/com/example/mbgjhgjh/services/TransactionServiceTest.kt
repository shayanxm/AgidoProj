package com.example.mbgjhgjh.services

import org.junit.jupiter.api.Assertions.*

import com.example.mbgjhgjh.controller.repository.TransactionRepo
import com.example.mbgjhgjh.controller.repository.UserRepo
import com.example.mbgjhgjh.controller.repository.dbmodel.TransactionDb
import com.example.mbgjhgjh.controller.repository.dbmodel.convertToTransaction
import com.example.mbgjhgjh.models.Transaction
import com.example.mbgjhgjh.models.Messager
import com.example.mbgjhgjh.models.convertToTransactionModel
import com.example.mbgjhgjh.models.Customer
import com.example.mbgjhgjh.services.TransactionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class TransactionServiceTest {

    @Mock
    lateinit var transactionRepo: TransactionRepo

    @Mock
    lateinit var userRepo: UserRepo

    lateinit var transactionService: TransactionService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        transactionService = TransactionService()
        transactionService.transactionRepo = transactionRepo
        transactionService.repository = userRepo
    }

    @Test
    fun testReduceAmount() {
        // Mock the behavior of transactioner() method for reducing amount
        val transaction = Transaction("user1", 50.0)
        val transactionerMessage = TransactionService.TransactionerMessage(
            true,
            "Transaction is successfully done! new balance for this user=  50.0 ",
            50.0
        )
        Mockito.`when`(transactionService.transactioner(transaction, false)).thenReturn(transactionerMessage)

        // Perform the test
        val result = transactionService.reduceAmount(transaction)

        // Assert the result
        assert(result == transactionerMessage)
    }

    @Test
    fun testIncreaseAmount() {
        // Mock the behavior of transactioner() method for increasing amount
        val transaction = Transaction("user1", 50.0)
        val transactionerMessage = TransactionService.TransactionerMessage(
            true,
            "Transaction is successfully done! new balance for this user=  50.0 ",
            50.0
        )
        Mockito.`when`(transactionService.transactioner(transaction, true)).thenReturn(transactionerMessage)

        // Perform the test
        val result = transactionService.increaseAmount(transaction)

        // Assert the result
        assert(result == transactionerMessage)
    }


    @Test
    fun testReduceMyAmount() {
        // Mock the behavior of transactioner() method for reducing my amount
        val transaction = Transaction("user1", 50.0)
        val transactionerMessage = TransactionService.TransactionerMessage(
            true,
            "Transaction is successfully done! new balance for this user=  50.0 ",
            50.0
        )
        Mockito.`when`(transactionService.transactioner(transaction, false)).thenReturn(transactionerMessage)

        // Perform the test
        val result = transactionService.reduceMyAmount(transaction)

        // Assert the result
        assert(result == transactionerMessage)
    }

    @Test
    fun testIncreaseMyAmount() {
        // Mock the behavior of transactioner() method for increasing my amount
        val transaction = Transaction("user1", 50.0)
        val transactionerMessage = TransactionService.TransactionerMessage(
            true,
            "Transaction is successfully done! new balance for this user=  50.0 ",
            50.0
        )
        Mockito.`when`(transactionService.transactioner(transaction, true)).thenReturn(transactionerMessage)

        // Perform the test
        val result = transactionService.increaseMyAmount(transaction)

        // Assert the result
        assert(result == transactionerMessage)
    }

    @Test
    fun testGetUserTransactions() {
        // Mock the behavior of transactionRepo.findAllByCustomerId()
        val user1Transaction1 = TransactionDb("user1", 50.0)
        val user1Transaction2 = TransactionDb("user1", -30.0)
        val user2Transaction = TransactionDb("user2", 20.0)
        val transactions = listOf(user1Transaction1, user1Transaction2)
        val arrayList = ArrayList(transactions)

        Mockito.`when`(transactionRepo.findAllByCustomerId("user1")).thenReturn(arrayList)

        // Perform the test
        val result = transactionService.getUserTransactions("user1")

        // Assert the result
        assert(result == transactions)
    }

    @Test
    fun testGetAll() {
        print("neeeeeeeeeee")
        // Mock the behavior of transactionRepo.findAll() and aresameDate() method
        val today = Date()
        val yesterday = Date(today.time - 86400000) // 1 day ago
        val transactions = listOf(
            TransactionDb("user1", 50.0,false, today),
            TransactionDb("user1", -30.0,false, today),
            TransactionDb("user1", 20.0,false, yesterday)
        )
        val transactionsMessage = Messager.TransactionsMessage(3, 0, 70.0, 30.0, 40.0)
        Mockito.`when`(transactionRepo.findAll()).thenReturn(transactions)
        Mockito.`when`(transactionService.aresameDate(today)).thenReturn(true)
        Mockito.`when`(transactionService.aresameDate(yesterday)).thenReturn(false)

        // Perform the test
        val result = transactionService.getAll()
        print("neeeeeeeeeee")

        // Assert the result
        assert(result == transactionsMessage)
    }

    @Test
    fun testGetDetail() {
        // Mock the behavior of transactionRepo.findAll() and aresameDate() method
        val today = Date()
        val yesterday = Date(today.time - 86400000) // 1 day ago
        val transactions = listOf(
            TransactionDb("user1", 50.0,false, today),
            TransactionDb("user1", -30.0,false, today),
            TransactionDb("user1", 20.0,false, yesterday)
        )
        val filteredTransactions = listOf(
            TransactionDb("user1", 50.0,false, today),
            TransactionDb("user1", -30.0,false, today)
        )
        Mockito.`when`(transactionRepo.findAll()).thenReturn(transactions)
        Mockito.`when`(transactionService.aresameDate(today)).thenReturn(true)
        Mockito.`when`(transactionService.aresameDate(yesterday)).thenReturn(false)

        // Perform the test
        val result = transactionService.getDetail()

        // Assert the result
        assert(result == filteredTransactions)
    }

    // Add more test cases for aresameDate(), Date.toLocalDate(), and other methods as needed
}
