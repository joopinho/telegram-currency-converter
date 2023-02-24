package com.jjj.currencyConverter.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ExchangeRateListUPDto(
    @JsonProperty("exchangeRateJson") val exchangeRateJson: List<ExchangeRateUPDto>
) {

}
