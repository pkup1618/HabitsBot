package app

import app.services.MessageHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
open class FinanceBotApplication

fun main(args: Array<String>) {
    val ctx: ConfigurableApplicationContext = runApplication<FinanceBotApplication>(*args)
    val messageHandler: MessageHandler = ctx.getBean("messageHandler", MessageHandler::class.java)

    messageHandler.start()
}