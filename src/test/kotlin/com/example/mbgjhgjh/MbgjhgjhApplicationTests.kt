package com.example.mbgjhgjh

import org.aspectj.bridge.Message
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import kotlin.random.Random

//@SpringBootTest(
//    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//    properties = [
//        "spring.datasource.url=jdbc:h2:file:./data/mydatabasee"
//    ]
//)
class MbgjhgjhApplicationTests() {


    @Test
    fun `base endpoint`() {
      //  val entity = client.getForEntity<String>("/hello")
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(entity.body).contains("hello")
    }


    /**
    make sure we post and get data correctly
     **/
    fun `testing if we can post and retrive the data`() {
//        val id = "${Random.nextInt()}".uuid()
//        val message = Message(id, "some message")
//        client.postForObject<Message>("/", message)


    }


    @Test
    fun contextLoads() {
    }

}
