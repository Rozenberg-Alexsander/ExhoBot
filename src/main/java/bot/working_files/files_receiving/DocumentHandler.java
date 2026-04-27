package bot.working_files.files_receiving;

import bot.cache.HashCacheService;
import bot.command.command_processing.Messages;
import bot.working_files.FileRequest;
import bot.working_files.files_downloader.FileDownloader;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class DocumentHandler {
    private final TypeFile typeFile;

    public DocumentHandler(Messages messages, FileDownloader fileDownloader, HashCacheService hashCacheService) {
        this.typeFile = new TypeFile(messages, fileDownloader, hashCacheService);
    }

    public void handleDocument(Message message) {
        Long chatId = message.getChatId();
        String fileId = message.getDocument().getFileId();
        String uniqueId = message.getDocument().getFileUniqueId();
        String fileName = message.getDocument().getFileName();

        typeFile.request(new FileRequest(fileId, uniqueId, fileName, chatId));
        System.out.println("Получен документ: " + fileName + " (ID: " + fileId + ")");
    }
}
