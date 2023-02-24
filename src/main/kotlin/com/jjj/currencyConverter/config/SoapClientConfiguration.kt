package com.jjj.currencyConverter.config

import com.jjj.currencyConverter.remote.impl.CBRFSoapClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller

@Configuration
class SoapClientConfiguration {

    @Value("\${cbrf.url}")
    lateinit var cbrfHost: String

    @Bean
    fun marshaller() = Jaxb2Marshaller().apply { setContextPaths("com.jjj.currencyConverter.wsdl") }

    @Bean
    fun cbrfSoapClient(marshaller: Jaxb2Marshaller) = CBRFSoapClient().also {
        it.defaultUri = cbrfHost
        it.marshaller = marshaller
        it.unmarshaller = marshaller
    }
}