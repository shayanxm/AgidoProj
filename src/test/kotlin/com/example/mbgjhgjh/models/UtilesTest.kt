package com.example.mbgjhgjh.models

import com.example.mbgjhgjh.models.Utiles.toLocalDate
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.Date

class UtilesTest {

    @Test
    fun testAresameDate_SameDate() {
        // Create a Date with the current date
        val currentDate = Date()

        // Call aresameDate with the current date
        val result = Utiles.aresameDate(currentDate)

        // Assert that the result is true because it's the same date
        assertTrue(result)
    }

    @Test
    fun testAresameDate_DifferentDate() {
        // Create a Date with a different date (1 day ago)
        val yesterday = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)

        // Call aresameDate with the different date
        val result = Utiles.aresameDate(yesterday)

        // Assert that the result is false because it's a different date
        assertFalse(result)
    }

    @Test
    fun testDateToLocalDate() {
        // Create a Date
        val date = Date()

        // Call Date.toLocalDate()
        val localDate = date.toLocalDate()

        // Get the current LocalDate
        val currentDate = LocalDate.now()

        // Assert that the LocalDate obtained from Date matches the current LocalDate
        assertEquals(currentDate, localDate)
    }
}
