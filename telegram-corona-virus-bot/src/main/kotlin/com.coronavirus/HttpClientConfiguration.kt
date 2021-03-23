package com.coronavirus

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfiguration {

    @Bean
    fun objectMapper() = jacksonObjectMapper()

    @Bean
    fun okHttpClient() = OkHttpClient()


}