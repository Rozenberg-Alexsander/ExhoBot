package bot.working_files.files_sending;

import bot.command.Command;
import bot.util.ResourceFileReader;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendFileCommand implements Command {
    private final TelegramClient client;

    public SendFileCommand(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        SendDocument sendDocument = SendDocument.builder()
                .chatId(chatId)
                .document(ResourceFileReader.getInputFileFromResources())
                .caption("Список")
                .build();

        try {
            client.execute(sendDocument);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка при отправке видеофайла");
        }
    }
}


