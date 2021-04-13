package com.sheduled

import io.chengine.ChengineTelegramLongPoolingBot
import io.chengine.command.HandleCommand
import io.chengine.dsl.sendTelegramMessage
import io.chengine.execute
import io.chengine.handler.Handler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Handler
@Component
@EnableScheduling
class SendingScheduler(val bot: ChengineTelegramLongPoolingBot) {

    /**
     * 1. Call this command from the bot to get yours chatId
     */
    @HandleCommand("/chat")
    fun getMyChatId(update: Update) = sendTelegramMessage {
        text = update.message?.chatId.toString()
    }

    /**
     * 2. Put received chatId here
     */
    private val chats = listOf(
        "putChatIdNumberHere", // <- Put here required chatIds
    )

    /**
     * 3. Uncomment @Scheduled annotation, restart the application and wait :)
     */
    //@Scheduled(cron = "*/5 * * * * *") // Send a message every five seconds
    fun scheduledSending() {
        chats.forEach { id ->
            bot.execute {
                sendTelegramMessage {
                    chatId = id
                    text = "Hey! Don't forget about me!"
                }
            }
        }
    }

}