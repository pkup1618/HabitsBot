package com.example.demo.bot

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi


class BotInitializer @Autowired constructor(
    private val telegramBot: TelegramBot,
    private val telegramBotsApi: TelegramBotsApi
) {
    @PostConstruct
    fun initBot() {
        telegramBotsApi.registerBot(telegramBot)
    }
}
