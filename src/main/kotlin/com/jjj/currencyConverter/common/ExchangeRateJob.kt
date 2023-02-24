package com.jjj.currencyConverter.common

import com.jjj.currencyConverter.dto.NotificationMessageDto
import com.jjj.currencyConverter.enums.CurrencyCode
import com.jjj.currencyConverter.remote.Notification
import com.jjj.currencyConverter.service.CurrencyService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@Component
class ExchangeRateJob(
    private val notification: Notification,
    private val converterService: CurrencyService
) {
    @Value("\${telegram.chatId}")
    lateinit var telegramChatId: String

    val logger: Logger = LoggerFactory.getLogger(ExchangeRateJob::class.java)

    @Scheduled(cron = "\${cron.expression}")
    fun sendActualAMDtoRURExchangeRate() {

        try {
            val amd2cnyRate =
                converterService.getExchangeRate(CurrencyCode.AMD, CurrencyCode.CNY, LocalDate.now().minusDays(1))
            val cny2rurRate = converterService.getExchangeRate(CurrencyCode.CNY, CurrencyCode.AMD, LocalDate.now())

            val amd2RurRate = BigDecimal(1.0)
                .divide(cny2rurRate.multiply(BigDecimal(1.04)), 8, RoundingMode.HALF_UP)
                .divide(amd2cnyRate, 2, RoundingMode.HALF_UP)

            val msg = NotificationMessageDto(
                telegramChatId,
                """AMD -> CNY (Union Pay): $amd2cnyRate
                   |CNY -> RUB (ЦБ РФ): $cny2rurRate
                   |AMD -> RUB (МТСБ) ~ $amd2RurRate""".trimMargin()
            )
            notification.send(msg)
        } catch (e: Exception) {
            logger.error(e.stackTraceToString())
        }
    }
}