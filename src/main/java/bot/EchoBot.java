package bot;

import bot.command.CommandDispatcher;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class EchoBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final CommandDispatcher commandDispatcher;

    public EchoBot(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        this.commandDispatcher = new CommandDispatcher(telegramClient);
    }


    @Override
    public void consume(Update update) {
        if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            var callbackData = callbackQuery.getData(); //ДАННЫЕ С КНОПКИ
            long chatId = callbackQuery.getMessage().getChatId(); //ID чата
            long messageId = callbackQuery.getMessage().getMessageId(); //ID сообщения

            // Обработка callbackData
            if (callbackData.equals("change_text")) {
//                var newText = "Чего тыкаешь?";
                var newText = "\uD83D\uDD95";
//                var newText = "\uD83D\uDE0A";

                // Создаём объект для изменения текста сообщения
                var editMessageText = EditMessageText.builder()
                        .chatId(chatId)
                        .messageId((int) messageId) //ID сообщения, которое будет изменено
                        .text(newText)
                        .build();

                try {
                    telegramClient.execute(editMessageText); // Изменяем текст сообщения
                    telegramClient.execute(new AnswerCallbackQuery(callbackQuery.getId())); // Подтверждаем обработку
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            System.out.println("There is no any text in Update" + update.getUpdateId());
            return;
        }

        String text = update.getMessage().getText().stripLeading();
        Long chatId = update.getMessage().getChatId();
        if (text.startsWith("/")) {
            commandDispatcher.dispatch(text, update);
            return;
        }

        System.out.println(text + " from chatId=" + chatId);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println(update.getMessage().getText());
    }
}
