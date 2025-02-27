package app

import app.services.PropertiesService
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
open class BotConfig @Autowired constructor(
    private val propertiesService: PropertiesService,
    private val telegramBotsApi: TelegramBotsApi,
) {
    init {
        telegramBotsApi.registerBot(telegramBot())
    }

    @Bean
    open fun defaultBotSession(): TelegramBotsApi {
        return TelegramBotsApi(DefaultBotSession::class.java)
    }

    @Bean
    open fun telegramBot(): TelegramBot {
        return TelegramBot(
            DefaultBotOptions(),
            propertiesService.loadTelegramBotProps().getProperty("token")
        )
    }

    @Bean
    fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()

        threadPoolTaskScheduler.poolSize = 5
        threadPoolTaskScheduler.threadNamePrefix = "NotificationThread"

        return threadPoolTaskScheduler
    }

    @Bean
    open fun modelDataSource(): DataSource {
        val dbConnectionInfo = propertiesService.loadDatabaseProps()

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