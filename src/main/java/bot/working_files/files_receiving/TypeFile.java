package bot.working_files.files_receiving;

import bot.cache.HashCacheService;
import bot.command.command_processing.Messages;
import bot.working_files.FileRequest;
import bot.working_files.files_downloader.FileDownloader;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Path;

public class TypeFile {
    private final Messages messages;
    private final FileDownloader fileDownloader;
    private final HashCacheService hashCacheService;

    public TypeFile(Messages messages, FileDownloader fileDownloader, HashCacheService hashCacheService) {
        this.messages = messages;
        this.fileDownloader = fileDownloader;
        this.hashCacheService = hashCacheService;
    }

    public void request(FileRequest fileRequest) {
        String hashCache = hashCacheService.getCash(fileRequest.uniqueId());
        if (hashCache != null) {
            System.out.println("Пользователь отправляет файла из кэша ");
        } else {
            try {
                File file = fileDownloader.getTelegramFile(fileRequest.fileId());
                String fileUrl = fileDownloader.accessFilesByUrl(file.getFilePath());
                Path saveFile = fileDownloader.downloadFile(fileUrl, fileRequest.fileName());

                String sha256 = fileDownloader.calculateSha256(saveFile);
                hashCacheService.saveCache(fileRequest.uniqueId(), sha256);

                messages.sendSimpleMessage(fileRequest.chatId(), "✅ Файл сохранен!\nSHA-256: `" + sha256 + "`");
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
