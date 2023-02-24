package com.jjj.currencyConverter.remote.impl

import com.jjj.currencyConverter.wsdl.GetCursOnDateXML
import com.jjj.currencyConverter.wsdl.GetCursOnDateXMLResponse
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar


class CBRFSoapClient : WebServiceGatewaySupport() {
    fun getCursOnDate(date: LocalDate): GetCursOnDateXMLResponse {

        val gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()))
        val xcal: XMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal)

        val request = GetCursOnDateXML()
        request.onDate = xcal

        return webServiceTemplate.marshalSendAndReceive(
            request,
            SoapActionCallback("http://web.cbr.ru/GetCursOnDateXML")
        ) as GetCursOnDateXMLResponse
    }
}