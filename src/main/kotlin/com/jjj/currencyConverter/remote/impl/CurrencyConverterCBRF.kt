package com.jjj.currencyConverter.remote.impl

import com.jjj.currencyConverter.enums.CurrencyCode
import com.jjj.currencyConverter.remote.CurrencyConverter
import com.jjj.currencyConverter.wsdl.ValuteData
import java.math.BigDecimal
import java.time.LocalDate

class CurrencyConverterCBRF(
    private val cbrfSoapClient: CBRFSoapClient
) :
    CurrencyConverter {

    override fun getExchangeRate(from: CurrencyCode, to: CurrencyCode, date: LocalDate): BigDecimal? {
        val valuteData = cbrfSoapClient.getCursOnDate(date)
            .getCursOnDateXMLResult?.any as ValuteData

        val valuteCursOnDateList = valuteData?.valuteCursOnDate

        return valuteCursOnDateList?.firstOrNull { it.vchCode == from.name }?.vcurs
    }
}