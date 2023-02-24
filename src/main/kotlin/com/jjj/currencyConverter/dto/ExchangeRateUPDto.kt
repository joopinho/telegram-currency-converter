package com.jjj.currencyConverter.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ExchangeRateUPDto(
    @JsonProperty("transCur") val transCur: String,
    @JsonProperty("baseCur") val baseCur: String,
    @JsonProperty("rateData") val rateData: BigDecimal
) {
}