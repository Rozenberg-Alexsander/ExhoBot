package bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

public class UserIdCommand implements Command {

    //    private final String answerPattern = "Ваш ID: `%s`"; //МОНОШИРНЫЙ ТЕКСТ
    private final String answerPattern = "<u>Ваш ID</u>: <code>%s</code>"; //HTML ФОРМАТ
    private final TelegramClient client;

    public UserIdCommand(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        /**
         * Inline кнопки
         */

        var buttonAction = InlineKeyboardButton.builder()
                .text("Нажми меня")
                .callbackData("change_text")
                .build();

        var buttonUrl = InlineKeyboardButton.builder()
                .url("https://github.com/Rozenberg-Alexsander?tab=repositories")
                .text("Мои репозитории")
                .build();

        var prevButton = InlineKeyboardButton.builder()
                .text("<<")
                .callbackData("prev_page")
                .build();

        var nextButton = InlineKeyboardButton.builder()
                .text(">>")
                .callbackData("next_page")
                .build();

        InlineKeyboardRow row1 = new InlineKeyboardRow(buttonAction, buttonUrl);
        InlineKeyboardRow row2 = new InlineKeyboardRow(prevButton, nextButton);

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(row1)
                .keyboardRow(row2)
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(answerPattern.formatted(chatId))
                .replyMarkup(inlineKeyboardMarkup)
                .parseMode("HTML")
                .build();

        try {
            client.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка при отправке /myid: " + e.getMessage());
        }

    }


////        /**
////         * Reply-кнопки
//
//        KeyboardRow row1 = new KeyboardRow();
//        row1.add("Кнопка 1");
//        row1.add("Кнопка 2");
//
//        KeyboardRow row2 = new KeyboardRow();
//        row2.add("Кнопка 3");
//
//        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
//                .keyboardRow(row1)
//                .keyboardRow(row2)
//                .resizeKeyboard(true)
//                .oneTimeKeyboard(true)
//                .inputFieldPlaceholder("Выберете вариант")
//                .build();
//
//        SendMessage sendMessage = SendMessage.builder()
//                .chatId(chatId)
//                .text(answerPattern.formatted(chatId))
//                .replyMarkup(replyKeyboardMarkup)
//                .parseMode("HTML")
//                .build();
//
//        try {
//            client.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//}
///
    }


