package com.sheduled

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Don't forget to fill your bot credentials in application.yml file
 *
 * If you don't know how to create telegram bot, please visit
 * https://core.telegram.org/bots#3-how-do-i-create-a-bot
 */
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
