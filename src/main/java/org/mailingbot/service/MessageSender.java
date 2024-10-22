package org.mailingbot.service;

import lombok.RequiredArgsConstructor;
import org.mailingbot.bot.MailingBot;
import org.mailingbot.dto.AccountDto;
import org.mailingbot.model.Account;
import org.mailingbot.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageSender {

    private final AccountService accountService;

    public void sendMessageToAllUsers(String message, MailingBot bot) {
        List<AccountDto> usersForMailing = accountService.getAllUsers();

        for (AccountDto user : usersForMailing) {
            try {
                bot.execute(new SendMessage(user.getChatId().toString(), message));
            } catch (TelegramApiException e) {
                System.out.println("Не получилось отправить сообщение. ");
            }
        }
    }
}
