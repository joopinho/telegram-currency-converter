package com.jjj.currencyConverter.remote

import com.jjj.currencyConverter.enums.CurrencyCode
import java.math.BigDecimal
import java.time.LocalDate

interface CurrencyConverter {
    fun getExchangeRate(from: CurrencyCode, to: CurrencyCode, date: LocalDate): BigDecimal?
}