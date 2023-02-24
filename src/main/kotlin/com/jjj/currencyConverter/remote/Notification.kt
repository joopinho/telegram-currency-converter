package com.jjj.currencyConverter.remote

import com.jjj.currencyConverter.dto.NotificationMessageDto

interface Notification {
    fun send(message: NotificationMessageDto)
}