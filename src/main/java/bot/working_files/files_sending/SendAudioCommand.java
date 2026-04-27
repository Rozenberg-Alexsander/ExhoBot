package bot.working_files.files_sending;

import bot.command.Command;
import bot.util.ResourceFileReader;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendAudioCommand implements Command {
    private final TelegramClient client;

    public SendAudioCommand(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        SendAudio sendAudio = SendAudio.builder()
                .chatId(chatId)
                .audio(ResourceFileReader.getInputAudioFromResources())
                .title("Rammstein")
                .performer("Du hast")
                .build();

        try {
            client.execute(sendAudio);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка при отправке аудиофайла");
        }
    }
}
