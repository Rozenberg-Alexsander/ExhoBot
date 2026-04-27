package bot.working_files.files_downloader;

import config.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileDownloader {
    private final TelegramClient telegramClient;
    private final Config config;
    private final Path savePaths = Path.of("C:/Users/Admin/Downloads/Tele/");

    public FileDownloader(TelegramClient telegramClient, Config configReaderEnvironment) {
        this.telegramClient = telegramClient;
        this.config = configReaderEnvironment;
    }

    public Path downloadFile(String fileUrl, String fileName) throws IOException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .build();

        try {
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

            try (InputStream is = response.body()) {

                Files.createDirectories(savePaths);
                Path outputPath = savePaths.resolve(fileName);
                Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Файл сохранён: " + savePaths);
                return outputPath;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Загрузка прервана", e);
        }
    }

    public File getTelegramFile(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile(fileId);
        return telegramClient.execute(getFile);
    }


    public String accessFilesByUrl(String filePath) {
        return "https://api.telegram.org/file/bot" + config.botApiToken() + "/" + filePath;
    }

    public String calculateSha256(Path file) throws IOException, TelegramApiException {
        try (InputStream is = Files.newInputStream(file)) {
            return DigestUtils.sha256Hex(is);
        }
    }
}
