package com.coronavirus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy::class)
data class CoronaVirusDto(
    var message: String? = null,
    var global: Global? = null,
    var countries: List<Country>? = null,
    var date: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy::class)
class Country(
    var country: String,
    var countryCode: String,
    var slug: String,
    var newConfirmed: Long,
    var totalConfirmed: Long,
    var newDeaths: Long,
    var totalDeaths: Long,
    var newRecovered: Long,
    var totalRecovered: Long
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy::class)
data class Global(
    var newConfirmed: Long,
    var totalConfirmed: Long,
    var newDeaths: Long,
    var totalDeaths: Long,
    var newRecovered: Long,
    var totalRecovered: Long
)