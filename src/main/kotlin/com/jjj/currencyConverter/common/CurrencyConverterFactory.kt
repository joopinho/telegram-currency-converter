package com.jjj.currencyConverter.common

import com.jjj.currencyConverter.enums.CurrencyCode
import com.jjj.currencyConverter.remote.CurrencyConverter
import com.jjj.currencyConverter.remote.impl.CBRFSoapClient
import com.jjj.currencyConverter.remote.impl.CurrencyConverterCBRF
import com.jjj.currencyConverter.remote.impl.CurrencyConverterUnionPay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CurrencyConverterFactory {

    @Value("\${union-pay.url}")
    lateinit var unionPayHost: String

    @Autowired
    lateinit var cbrfSoapClient: CBRFSoapClient

    fun createConverter(baseCurrency: CurrencyCode): CurrencyConverter = when (baseCurrency) {
        CurrencyCode.CNY -> CurrencyConverterUnionPay(unionPayHost)
        CurrencyCode.RUB -> CurrencyConverterCBRF(cbrfSoapClient)
        else -> CurrencyConverterCBRF(cbrfSoapClient)
    }

}
