package app

import app.services.JdbcOperationsService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

//todo можно переделать через спринговый создатель тасков

@Component
class NotificationScheduler @Autowired constructor(
    private val threadPoolTaskScheduler: ThreadPoolTaskScheduler,
    private val jdbcOperationsService: JdbcOperationsService,
    private val telegramBot: TelegramBot
) {
    @PostConstruct
    fun initiateScheduling() {
        val habitsForSchedule: List<Habit> = jdbcOperationsService.getAllTrackingHabits()

        for (habit in habitsForSchedule) {
            threadPoolTaskScheduler.schedule(
                MessagePrinterTask(telegramBot, habit),
                CronTrigger(habit.notifictaionCron!!)
            )
        }
    }
}

class MessagePrinterTask(
    private val telegramBot: TelegramBot,
    private val habit: Habit
) : Runnable {
    override fun run() {
        val notification = SendMessage()

        notification.setChatId(habit.chatMemberId)
        notification.text = habit.toString()

        telegramBot.execute(notification)
    }
}