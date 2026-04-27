package bot.working_files.files_receiving;

import bot.cache.HashCacheService;
import bot.command.command_processing.Messages;
import bot.working_files.FileRequest;
import bot.working_files.files_downloader.FileDownloader;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class VideoHandler { ;
    private final TypeFile typeFile;

    public VideoHandler(Messages messages, FileDownloader fileDownloader, HashCacheService hashCacheService) {
        this.typeFile = new TypeFile(messages, fileDownloader, hashCacheService);
    }

    public void handleVideo(Message message) {
        Long chatId = message.getChatId();
        String fileId = message.getVideo().getFileId();
        String fileName = message.getVideo().getFileName();
        String uniqueId = message.getVideo().getFileUniqueId();

        if (fileName == null || fileName.isEmpty()) {
            fileName = "video_" + uniqueId + ".mp4";
        }
        typeFile.request(new FileRequest(fileId, uniqueId, fileName, chatId));
        System.out.println("Принят видеофайл " + fileName + " (ID: " + fileId + ")");
    }
}
