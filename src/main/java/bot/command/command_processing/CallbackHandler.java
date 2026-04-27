package bot.command.command_processing;

import bot.command.CommandDispatcher;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CallbackHandler {
    private final TelegramClient telegramClient;

    public CallbackHandler(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        var callbackData = callbackQuery.getData(); //ДАННЫЕ С КНОПКИ
        long chatId = callbackQuery.getMessage().getChatId(); //ID чата
        long messageId = callbackQuery.getMessage().getMessageId(); //ID сообщения

        // Обработка callbackData
        if (callbackData.equals("change_text")) {
            // Создаём объект для изменения текста сообщения
            var editMessageText = EditMessageText.builder()
                    .chatId(chatId)
                    .messageId((int) messageId) //ID сообщения, которое будет изменено
                    .text("\uD83D\uDD95")
                    .build();

            try {
                telegramClient.execute(editMessageText); // Изменяем текст сообщения
                telegramClient.execute(new AnswerCallbackQuery(callbackQuery.getId())); // Подтверждаем обработку
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
