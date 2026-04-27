package bot;

import bot.cache.HashCacheService;
import bot.command.CommandDispatcher;
import bot.command.command_processing.*;
import bot.working_files.files_downloader.FileDownloader;
import bot.working_files.files_receiving.AudioHandler;
import bot.working_files.files_receiving.DocumentHandler;
import bot.working_files.files_receiving.PhotoHandler;
import bot.working_files.files_receiving.VideoHandler;
import config.Config;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class EchoBot implements LongPollingSingleThreadUpdateConsumer {
    private final CallbackHandler callbackHandler;
    private final DocumentHandler documentHandler;
    private final TextHandler textHandler;
    private final AudioHandler audioHandler;
    private final PhotoHandler photoHandler;
    private final VideoHandler videoHandler;
    private final HashCacheService hashCacheService;

    public EchoBot(TelegramClient telegramClient, Config config) {
        this.hashCacheService = new HashCacheService();
        FileDownloader fileDownloader = new FileDownloader(telegramClient, config);
        CommandDispatcher commandDispatcher = new CommandDispatcher(telegramClient);
        Messages messages = new Messages(telegramClient);

        this.callbackHandler = new CallbackHandler(telegramClient);
        this.documentHandler = new DocumentHandler(messages, fileDownloader, hashCacheService);
        this.textHandler = new TextHandler(commandDispatcher, messages);
        this.photoHandler = new PhotoHandler(messages, fileDownloader,hashCacheService);
        this.videoHandler = new VideoHandler(messages, fileDownloader, hashCacheService);
        this.audioHandler = new AudioHandler(messages, fileDownloader, hashCacheService);

    }

    @Override
    public void consume(Update update) {
        if (update.hasCallbackQuery()) {
            callbackHandler.handleCallback(update.getCallbackQuery());
            return;
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                textHandler.handleTextMessage(update);
            } else if (message.hasDocument()) {
                documentHandler.handleDocument(message);
            } else if (message.hasAudio()) {
                audioHandler.handleAudio(message);
            } else if (message.hasPhoto()) {
                photoHandler.handlePhoto(message);
            } else if (message.hasVideo()) {
                videoHandler.handleVideo(message);
            } else {
                System.out.println("Тип сообщения пока не поддерживается (стикер, видео и т.д.)");
            }
        }
    }
}
