package com.example.mbgjhgjh.controller

import com.example.mbgjhgjh.models.Customer
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class CustomerControllerTestX @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    val baseUrl = "/api/customer"

//    @DisplayName("POST /api/customer")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class PostNewUser {
//        @Test
//        // @DirtiesContext
//        fun `should add new user`() {
//            // given
//            val newUser = Customer("testid")
//            newUser.password = "111111"
//            newUser.firstName = "shayan"
//            newUser.lastName = "moradi"
//            //     val newBank = BankDTO("acc123", 31.25, 2)
//            // when/then
//            mockMvc.post(baseUrl) {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(newUser)
//            }
//                .andDo { print() }
//                .andExpect {
//                    status { isCreated() }
//                    content {
//                        contentType(MediaType.APPLICATION_JSON)
//                        json(objectMapper.writeValueAsString(newUser))
//                    }
//                }
//            mockMvc.get("$baseUrl/${newBank.accountNumber}")
//                .andDo { print() }
//                .andExpect {
//                    status { isOk() }
//                    content {
//                        contentType(MediaType.APPLICATION_JSON)
//                        json(objectMapper.writeValueAsString(newBank))
//                    }
//                }
//        }
//
//
//    }
    //////////////////////////////////////////
    @Test
    fun `should return BAD REQUEST if user with given username  already exists`() {
        // given
        val invalidCustomer = Customer("shayan")
        // when/then
        mockMvc.post(baseUrl+"new") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidCustomer)
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
    }

    //////////////////////////////////////////
    @Test
    fun `should return BAD REQUEST if user with given password is smaller than 6 chars`() {
        // given
        val invalidCusotmer = Customer("shayan")
        invalidCusotmer.password="11111"
        // when/then
        mockMvc.post(baseUrl+"new") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidCusotmer)
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
    }
}
