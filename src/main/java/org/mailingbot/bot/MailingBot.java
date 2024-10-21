package org.mailingbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.awt.*;
import java.util.List;

@Component
public class MailingBot extends TelegramLongPollingBot {

    private String botToken;

    private String botName;

    private final UpdateHandler updateHandler;

    public MailingBot(@Value("${bot.name}") String botName,
                     @Value("${bot.token}") String botToken,
                     UpdateHandler updateHandler
    ) {
//        super(botToken);
        this.botName = botName;
        this.botToken = botToken;
        this.updateHandler = updateHandler;
    }


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = updateHandler.handleUpdate(update);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Не отправилось сообщение пользователю: " + message.getChatId());
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            onUpdateReceived(update);
        }
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
