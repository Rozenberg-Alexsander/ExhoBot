package bot.working_files;

public record FileRequest(
        String fileId,
        String uniqueId,
        String fileName,
        Long chatId
) { }
