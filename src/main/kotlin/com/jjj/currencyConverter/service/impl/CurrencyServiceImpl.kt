package com.jjj.currencyConverter.service.impl

import com.jjj.currencyConverter.common.CurrencyConverterFactory
import com.jjj.currencyConverter.enums.CurrencyCode
import com.jjj.currencyConverter.service.CurrencyService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class CurrencyServiceImpl(private val currencyConverterFactory: CurrencyConverterFactory) : CurrencyService {

    override fun getExchangeRate(from: CurrencyCode, to: CurrencyCode, date: LocalDate): BigDecimal {
        val converter = currencyConverterFactory.createConverter(to)
        return converter.getExchangeRate(from, to, date)!!
    }
}