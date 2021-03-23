package com.coronavirus

import io.chengine.command.CommandParameter
import io.chengine.command.HandleCommand
import io.chengine.dsl.ParseMode
import io.chengine.dsl.sendTelegramMessage
import io.chengine.handler.Handler
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Handler
@Component
class CovidInfoHandler(private val covidApiService: CovidApiService) {

    private val logger = logger()

    @HandleCommand("/covid")
    fun globalStatistics(update: Update) =
        sendTelegramMessage {

            logger.info { "Statistics requested by the user: ${update.message.from.id}" }

            covidApiService.globalStatistics()?.let {
                text = """
                    Global statistics over the world:
                    
                    New confirmed: ${it.newConfirmed}
                    New deaths: ${it.newDeaths}
                    New recovered: ${it.newRecovered}
                    Total confirmed: ${it.totalConfirmed}
                    Total deaths: ${it.totalDeaths}
                    Total recovered: ${it.totalRecovered}
                """.trimIndent()
            } ?: run {
                text = "Sorry, service is temporarily unavailable :("
            }
        }

    /**
     * Send /covid#country_code command to get statistics for a specific country.
     * For example /covid#us returns information about US issues
     */
    @HandleCommand("/covid#")
    fun countryStatistics(@CommandParameter("covid") countryCode: String) =
        sendTelegramMessage {
            covidApiService.countryStatistics(countryCode)?.let {
                text = """
                    Country statistics:
                    
                    Country: *${it.country}*
                    New confirmed: *${it.newConfirmed}*
                    New deaths: *${it.newDeaths}*
                    New recovered: *${it.newRecovered}*
                    Total confirmed: *${it.totalConfirmed}*
                    Total deaths: *${it.totalDeaths}*
                    Total recovered: *${it.totalRecovered}*
                    
                    _Please be attentive to yourself and your loved ones ❤️_
                """.trimIndent()

                // You can set parse mode to make your messages more beautiful
                parseMode = ParseMode.MARKDOWN
            } ?: run {
                text = "Sorry, the statistics is unavailable for the country code $countryCode"
            }
        }
}