package bot.command.command_processing;

import bot.command.CommandDispatcher;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TextHandler {
    private final CommandDispatcher commandDispatcher;
    private final Messages messages;

    public TextHandler(CommandDispatcher commandDispatcher, Messages messages) {
        this.commandDispatcher = commandDispatcher;
        this.messages = messages;
    }

    public void handleTextMessage(Update update) {
        String text = update.getMessage().getText().stripLeading();
        Long chatId = update.getMessage().getChatId();

        if (text.startsWith("/")) {
            commandDispatcher.dispatch(text, update);
        } else {
            messages.sendSimpleMessage(chatId, text);
        }
    }
}
