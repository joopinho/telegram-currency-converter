package com.jjj.currencyConverter.remote.impl

import com.jjj.currencyConverter.dto.NotificationMessageDto
import com.jjj.currencyConverter.remote.Notification
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TelegramNotification : Notification {
    @Value("\${telegram.host}")
    lateinit var host: String

    @Value("\${telegram.token}")
    lateinit var token: String

    override fun send(message: NotificationMessageDto) {
        val queryParams: HashMap<String, String> = hashMapOf("chat_id" to message.receiverId, "text" to message.text)
        RestTemplate().postForLocation(
            "$host/bot$token/sendMessage?chat_id={chat_id}&text={text}",
            null, queryParams
        )
    }
}