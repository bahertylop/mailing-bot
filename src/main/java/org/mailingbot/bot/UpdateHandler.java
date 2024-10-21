package org.mailingbot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateHandler {

    public SendMessage handleUpdate(Update update) {

        if (update.hasMessage()) {
            return handleMessage(update.getMessage());
        } else {
            return new SendMessage();
        }
    }

    private SendMessage handleMessage(Message message) {

        return new SendMessage();
    }
}
