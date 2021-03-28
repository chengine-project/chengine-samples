package com.gallery

import io.chengine.annotation.EnableTelegramCommandDescription
import io.chengine.command.CommandDescription
import io.chengine.command.CommandParameter
import io.chengine.command.HandleCommand
import io.chengine.dsl.*
import io.chengine.handler.Handler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * Take a look at [EnableTelegramCommandDescription] annotation. It enables command description
 * generation and binds it with `/help` command by default.
 */
@Handler
@Component
@EnableTelegramCommandDescription
class GalleryHandler(private val galleryService: GalleryService) {

    companion object {
        const val GALLERY = "/gallery"
        const val CLOSE_GALLERY = "/closeGallery"
        const val NEXT = "/next#"
        const val PREVIOUS = "/previous#"
    }

    /**
     * This is a public command. You may annotate in by [CommandDescription] to generate a description
     */
    @HandleCommand(GALLERY)
    @CommandDescription("Shows animal gallery")
    fun gallery() =
        sendTelegramPhoto {
            galleryService.getPhoto()?.let {
                photo {
                    name = it.image
                }
                caption = it.caption
                parseMode = ParseMode.MARKDOWN
                inlineKeyboard {
                    row {
                        button {
                            text = "Previous"
                            callbackData = "$PREVIOUS${it.previousId}"
                        }
                        button {
                            text = "Next"
                            callbackData = "$NEXT${it.nextId}"
                        }
                    }
                    row {
                        button {
                            text = "Close gallery"
                            callbackData = CLOSE_GALLERY
                        }
                    }
                }
            }
        }

    // *********************************************************************************************************
    /**
     * Non public command, not recommended to [CommandDescription] annotation over them.
     */

    @HandleCommand(NEXT)
    fun nextPhoto(@CommandParameter("next") id: Int) =
        editTelegramMedia {
            galleryService.getPhoto(id)?.let {
                editInlineKeyboardMarkup {
                    editButton(0, 0) {
                        callbackData = "$PREVIOUS$id"
                    }
                    editButton(0, 1) {
                        callbackData = "$NEXT${it.nextId}"
                    }
                }
            }
        }

    @HandleCommand(PREVIOUS)
    fun previousPhoto(@CommandParameter("previous") id: Int) =
        editTelegramMedia {
            galleryService.getPhoto(id)?.let {
                editInlineKeyboardMarkup {
                    editButton(0, 0) {
                        callbackData = "$PREVIOUS${it.previousId}"
                    }
                    editButton(0, 1) {
                        callbackData = "$NEXT${id}"
                    }
                }
            }
        }

    @HandleCommand(CLOSE_GALLERY)
    fun closeGallery(update: Update) =
        deleteTelegramMessage {
            messageId = update.callbackQuery?.message?.messageId
        }
}