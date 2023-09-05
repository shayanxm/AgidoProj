package com.example.mbgjhgjh.models

import org.junit.jupiter.api.Assertions.*

import com.example.mbgjhgjh.models.Customer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


class CustomerTest {

    lateinit var customer: Customer

    @BeforeEach
    fun setUp() {
        customer = Customer("testUser")
        customer.password = "testPassword"
        customer.firstName = "John"
        customer.lastName = "Doe"
        customer.gutHaben = 100.0
    }

    @Test
    fun testComparePassword() {
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode("test")
      //  println("------------------"+        BCryptPasswordEncoder().matches("testPassword", customer.password))
        customer.secureMyPassword()
      assertTrue(customer.comparePassword("testPassword"))
     //   assertEquals(customer.comparePassword("test"),hashedPassword)


        // assertFalse(customer.comparePassword("wrongPassword"))
    }

    @Test
    fun testEquals() {
        val customer1 = Customer("testUser")
        val customer2 = Customer("otherUser")

        assertTrue(customer == customer1)
        assertFalse(customer == customer2)
    }

    @Test
    fun testHashCode() {
        val customer1 = Customer("testUser")
        val customer2 = Customer("otherUser")

        assertEquals(customer.hashCode(), customer1.hashCode())
        assertNotEquals(customer.hashCode(), customer2.hashCode())
    }

    @Test
    fun testSecureMyPassword() {
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode("testPassword")

        customer.secureMyPassword()

        assertTrue(encoder.matches("testPassword", customer.password))
    }
}
