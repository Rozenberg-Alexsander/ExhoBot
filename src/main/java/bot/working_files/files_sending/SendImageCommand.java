package bot.working_files.files_sending;

import bot.command.Command;
import bot.util.ResourceFileReader;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendImageCommand implements Command {
    private final TelegramClient telegramClient;

    public SendImageCommand(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .photo(ResourceFileReader.getInputImageFromResources())
                .caption("Фото")
                .build();

        try {
            telegramClient.execute(sendPhoto);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка при отправке файла");
        }
    }
}
