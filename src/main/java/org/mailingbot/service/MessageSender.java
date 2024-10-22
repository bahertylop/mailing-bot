package org.mailingbot.service;

import lombok.RequiredArgsConstructor;
import org.mailingbot.bot.MailingBot;
import org.mailingbot.dto.AccountDto;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
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
                Thread.sleep(100);
            } catch (TelegramApiException e) {
                System.out.println("Не получилось отправить сообщение. ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public void copyMessageAndSendToAllUsers(Long chatIdFrom, Integer messageId, MailingBot bot) {
        List<AccountDto> usersForMailing = accountService.getAllUsers();

        CopyMessage copyMessage = new CopyMessage();

        copyMessage.setFromChatId(chatIdFrom);
        copyMessage.setMessageId(messageId);

        for (AccountDto user : usersForMailing) {
            try {
                copyMessage.setChatId(user.getChatId());

                bot.execute(copyMessage);
                Thread.sleep(100);
            } catch (TelegramApiException e) {
                System.out.println("Не получилось отправить сообщение. ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
