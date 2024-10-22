package org.mailingbot.bot;

import lombok.RequiredArgsConstructor;
import org.mailingbot.dto.AdminDto;
import org.mailingbot.model.Admin;
import org.mailingbot.service.AccountService;
import org.mailingbot.service.AdminService;
import org.mailingbot.service.MessageSender;
import org.mailingbot.util.ReplyConstants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final AccountService accountService;

    private final AdminService adminService;

    private final MessageSender messageSender;

    public SendMessage handleUpdate(Update update, MailingBot bot) {

        if (update.hasMessage()) {
            return handleMessage(update.getMessage(), bot);
        } else {
            return new SendMessage("0", "Пожалуйста, отправьте текстовое сообщение. ");
        }
    }

    private SendMessage handleMessage(Message message, MailingBot bot) {
        Long chatId = message.getChatId();
        String text = message.getText();

        Optional<AdminDto> adminOp = adminService.getAdminDtoByChatId(chatId);

        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(chatId);

        if (adminOp.isPresent()) {
            AdminDto admin = adminOp.get();
            switch (text) {
                case "/start" -> {
//                    accountService.registerNewAccount(message);
                    replyMessage.setText(ReplyConstants.START_MESSAGE_ADMIN);
                }
                case "/newMessage" -> {
                    if (admin.getBotStage().equals(Admin.BotStage.DEFAULT)) {
                        adminService.changeStage(chatId, Admin.BotStage.MESSAGE);
                        replyMessage.setText("Отправьте сообщение для рассылки.");
                    } else {
                        adminService.changeStage(chatId, Admin.BotStage.DEFAULT);
                        replyMessage.setText("Неправильно введена команда");
                    }
                }
                case "/confirmMessage" -> {
                    if (admin.getBotStage().equals(Admin.BotStage.CONFIRM)) {
                        messageSender.sendMessageToAllUsers(admin.getMessage(), bot);
                        replyMessage.setText("Сообщение отправлено");
                        adminService.changeStage(chatId, Admin.BotStage.DEFAULT);
                    } else {
                        adminService.changeStage(chatId, Admin.BotStage.DEFAULT);
                        replyMessage.setText("Неправильно введена команда");
                    }
                }
                default -> {
                    if (admin.getBotStage().equals(Admin.BotStage.MESSAGE)) {
                        adminService.changeStage(chatId, Admin.BotStage.CONFIRM);
                        adminService.saveNewMessage(chatId, text);
                        replyMessage.setText("подтвердите отправку: " + text);
                    } else {
                        adminService.changeStage(chatId, Admin.BotStage.DEFAULT);
                        replyMessage.setText("Неправильно введена команда");
                    }
                }
            }
        } else {
            if (text.equals("/start")) {
                accountService.registerNewAccount(message);
                replyMessage.setText(ReplyConstants.START_MESSAGE_USER);
            } else {
                replyMessage.setText(ReplyConstants.DEFAULT_MESSAGE_USERS);
            }
        }

        return replyMessage;
    }
}
