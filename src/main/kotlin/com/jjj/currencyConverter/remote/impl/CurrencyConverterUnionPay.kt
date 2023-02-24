package com.jjj.currencyConverter.remote.impl

import com.jjj.currencyConverter.dto.ExchangeRateListUPDto
import com.jjj.currencyConverter.enums.CurrencyCode
import com.jjj.currencyConverter.remote.CurrencyConverter
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyConverterUnionPay(private val host: String) : CurrencyConverter {

    override fun getExchangeRate(from: CurrencyCode, to: CurrencyCode, date: LocalDate): BigDecimal? {

        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("user-agent", "Mozilla/5.0 Firefox/26.0")

        val dateString = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val entity: HttpEntity<String> = HttpEntity<String>("parameters", headers)
        val response: ResponseEntity<ExchangeRateListUPDto> = RestTemplate().exchange(
            "$host/$dateString.json", HttpMethod.GET, entity,
            ExchangeRateListUPDto::class.java
        )
        val rates: ExchangeRateListUPDto = response.body!!

        return rates.exchangeRateJson.firstOrNull { it.transCur == from.name && it.baseCur == to.name }?.rateData
    }
}