package com.jjj.currencyConverter.service

import com.jjj.currencyConverter.enums.CurrencyCode
import java.math.BigDecimal
import java.time.LocalDate

interface CurrencyService {
    fun getExchangeRate(from: CurrencyCode, to: CurrencyCode, date: LocalDate): BigDecimal
}