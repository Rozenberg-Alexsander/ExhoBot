package bot.working_files.files_sending;

import bot.command.Command;
import bot.util.ResourceFileReader;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendVideoCommand implements Command {
    private final TelegramClient client;

    public SendVideoCommand(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        SendVideo video = SendVideo.builder()
                .chatId(chatId)
                .video(ResourceFileReader.getInputVideoFromResources())
                .caption("Видео")
                .build();

        try {
            client.execute(video);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка при отправке файла");
        }
    }
}
