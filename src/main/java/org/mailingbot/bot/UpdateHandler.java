package org.mailingbot.bot;

import lombok.RequiredArgsConstructor;
import org.mailingbot.dto.AdminDto;
import org.mailingbot.model.Admin;
import org.mailingbot.service.AccountService;
import org.mailingbot.service.AdminService;
import org.mailingbot.service.MessageSender;
import org.mailingbot.util.KeyBoards;
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

        if (text == null) {
            text = "";
//            return new SendMessage(chatId.toString(), "Пожалуйста, отправьте текстовое сообщение. ");
        }

        Optional<AdminDto> adminOp = adminService.getAdminDtoByChatId(chatId);

        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(chatId);

        if (adminOp.isPresent()) {
            AdminDto admin = adminOp.get();
            Admin.BotStage newStage;
            switch (text) {
                case "/start" -> {
                    replyMessage.setText(ReplyConstants.START_MESSAGE_ADMIN);
                    newStage = Admin.BotStage.DEFAULT;
                }
                case "/newMessage" -> {
                    if (admin.getBotStage().equals(Admin.BotStage.DEFAULT)) {
                        newStage = Admin.BotStage.MESSAGE;
                        replyMessage.setText(ReplyConstants.NEW_MESSAGE_COMMAND_REPLY);
                    } else {
                        newStage = Admin.BotStage.DEFAULT;
                        replyMessage.setText(ReplyConstants.WRONG_COMMAND_REPLY);
                    }
                }
                case "/confirmMessage" -> {
                    if (admin.getBotStage().equals(Admin.BotStage.CONFIRM)) {
                        messageSender.copyMessageAndSendToAllUsers(admin.getChatId(), admin.getMessageId(), bot);
                        replyMessage.setText(ReplyConstants.MESSAGE_SENDED_REPLY);
                    } else {
                        replyMessage.setText(ReplyConstants.WRONG_COMMAND_REPLY);
                    }
                    newStage = Admin.BotStage.DEFAULT;
                }
                default -> {
                    if (admin.getBotStage().equals(Admin.BotStage.MESSAGE)) {
                        newStage = Admin.BotStage.CONFIRM;
                        adminService.saveNewMessage(chatId, message.getMessageId());
                        replyMessage.setText(ReplyConstants.MESSAGE_CONFIRM_REPLY + text);
                    } else {
                        newStage = Admin.BotStage.DEFAULT;
                        replyMessage.setText(ReplyConstants.WRONG_COMMAND_REPLY);
                    }
                }
            }
            adminService.changeStage(chatId, newStage);
            replyMessage.setReplyMarkup(KeyBoards.chooseKeyboardByStage(newStage));
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
