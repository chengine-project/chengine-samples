package com.coronavirus

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class CovidApiService(
    private val json: ObjectMapper,
    private val httpClient: OkHttpClient
) {

    private val apiUrl = "https://api.covid19api.com/summary"

    fun getStatistics(): CoronaVirusDto? {
        val request = Request.Builder().url(apiUrl).build()
        return httpClient.newCall(request).execute().use {
            it.body?.string()?.let { responseString ->
                return@use json.readValue<CoronaVirusDto>(responseString)
            }
        }
    }

    fun globalStatistics() = getStatistics()?.global

    fun countryStatistics(country: String) =
        getStatistics()?.countries?.firstOrNull {
            it.countryCode.toLowerCase() == country
        }

}