package com.example.mbgjhgjh


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication(exclude= [SecurityAutoConfiguration::class])
class MbgjhgjhApplication{
//	@Bean
//	fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()
}

fun main(args: Array<String>) {
	runApplication<MbgjhgjhApplication>(*args)

}
