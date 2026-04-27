package bot.working_files.files_receiving;

import bot.cache.HashCacheService;
import bot.command.command_processing.Messages;
import bot.working_files.FileRequest;
import bot.working_files.files_downloader.FileDownloader;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class AudioHandler {

    private final TypeFile typeFile;

    public AudioHandler(Messages messages, FileDownloader fileDownloader, HashCacheService hashCacheService) {
        this.typeFile = new TypeFile(messages, fileDownloader, hashCacheService);
    }

    public void handleAudio(Message message) {
        Long chatId = message.getChatId();
        String uniqueId = message.getAudio().getFileUniqueId();
        String fileId = message.getAudio().getFileId();
        String fileName = message.getAudio().getFileName();

        if (fileName == null) {
            fileName = fileId.substring(0, 10) + ".mp3";
            System.out.println(fileName);
        }

        typeFile.request(new FileRequest(fileId, uniqueId, fileName, chatId));
        System.out.println("Принят аудиофайл " + fileName + " (ID: " + fileId + ")");
    }
}

