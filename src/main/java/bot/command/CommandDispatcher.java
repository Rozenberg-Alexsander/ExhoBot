package bot.command;

import bot.working_files.files_sending.SendAudioCommand;
import bot.working_files.files_sending.SendFileCommand;
import bot.working_files.files_sending.SendImageCommand;
import bot.working_files.files_sending.SendVideoCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {
    private Map<String, Command> commandMap = new HashMap<>();

    public CommandDispatcher(TelegramClient telegramClient) {
        this.commandMap = new HashMap<>(Map.of(
                "/start", new StartCommand(telegramClient),
                "/myid", new UserIdCommand(telegramClient),
                "/file", new SendFileCommand(telegramClient),
                "/photo", new SendImageCommand(telegramClient),
                "/audio", new SendAudioCommand(telegramClient),
                "/video", new SendVideoCommand(telegramClient)
        ));
    }

    public void dispatch(String commandText, Update update) {
        Command command = commandMap.get(commandText.split("\\s+")[0].toLowerCase());

        System.out.printf("Обработка команды %s%n", commandText);
        if (command != null) {
            command.execute(update);
        } else {
            System.out.printf("Команда %s не найдена%n", commandText);
        }
    }
}
