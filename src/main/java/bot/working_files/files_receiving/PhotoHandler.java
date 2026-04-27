package bot.working_files.files_receiving;

import bot.cache.HashCacheService;
import bot.command.command_processing.Messages;
import bot.working_files.FileRequest;
import bot.working_files.files_downloader.FileDownloader;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;

public class PhotoHandler {
    private final TypeFile typeFile;

    public PhotoHandler(Messages messages, FileDownloader fileDownloader, HashCacheService hashCacheService) {
        this.typeFile = new TypeFile(messages, fileDownloader, hashCacheService);
    }

    public void handlePhoto(Message message) {
        PhotoSize photo = message.getPhoto().getLast();
        String fileId = photo.getFileId();
        String uniqueId = message.getPhoto().getLast().getFileUniqueId();
        String fileName = "photo_" + uniqueId + ".jpg";

        typeFile.request(new FileRequest(fileId, uniqueId, fileName, message.getChatId()));

        System.out.println("Получено фото: " + fileId + " (ID: " + photo + ")");
    }
}

