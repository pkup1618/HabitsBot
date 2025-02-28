package app

import app.services.PropertiesProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import javax.sql.DataSource


@Configuration
open class BotConfig(
    private val propertiesProvider: PropertiesProvider,
) {
    @Bean
    open fun defaultBotSession(): TelegramBotsApi {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        telegramBotsApi.registerBot(telegramBot())

        return telegramBotsApi
    }

    @Bean
    open fun telegramBot(): TelegramBot {
        return TelegramBot(
            DefaultBotOptions(),
            propertiesProvider.loadTelegramBotProps().getProperty("token")
        )
    }

    @Bean
    open fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()

        threadPoolTaskScheduler.poolSize = 5
        threadPoolTaskScheduler.threadNamePrefix = "NotificationThread"

        return threadPoolTaskScheduler
    }

    @Bean
    open fun modelDataSource(): DataSource {
        val dbConnectionInfo = propertiesProvider.loadDatabaseProps()

        return DataSourceBuilder
            .create()
            .username(dbConnectionInfo.getProperty("username"))
            .password(dbConnectionInfo.getProperty("password"))
            .url(dbConnectionInfo.getProperty("db_url"))
            .driverClassName(dbConnectionInfo.getProperty("driver"))
            .build()
    }
}

class TelegramBot(
    botOptions: DefaultBotOptions,
    botToken: String,
) : TelegramLongPollingBot(botOptions, botToken) {
    val receivedUpdates: Queue<Update> = ConcurrentLinkedQueue()

    override fun onUpdateReceived(update: Update) {
        receivedUpdates.add(update)
    }

    override fun getBotUsername(): String = "Презентационный бот"
}