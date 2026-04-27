import bot.EchoBot;
import config.Config;
import config.ConfigReader;
import config.ConfigReaderEnvironment;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        ConfigReader configReader = new ConfigReaderEnvironment();
        Config config = configReader.read();

        TelegramClient telegramClient = new OkHttpTelegramClient(config.botApiToken());

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(config.botApiToken(), new EchoBot(telegramClient, config));
            System.out.println("Бот запущен");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
