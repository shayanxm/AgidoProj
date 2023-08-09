package com.example.mbgjhgjh.backend

import com.example.mbgjhgjh.models.Customer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CustomerControllerTest{
    //private val CustomerController= CustomerController(repository = Repository)
    @Test
    fun `should provide a collection of banks`(){

        //given

        //when
   // val user = CustomerController.getAll()

        //then

      //  assertThat(user).isNotEmpty

    }
    @Test
    fun `should provide a some mock data`(){

        //given

        //when
        // val user = CustomerController.getAll()
val list = listOf(Customer("",""))
        //then

        //  assertThat(user).isNotEmpty
        assertThat(list).allMatch {it.passWord.length>=6}

    }


}