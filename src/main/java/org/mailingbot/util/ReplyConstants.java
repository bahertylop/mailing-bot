package org.mailingbot.util;

import org.mailingbot.dto.AccountDto;

import java.util.List;

public class ReplyConstants {

    public static final String START_MESSAGE_USER = "Вы подписаны на рассылку сообщений. ";

    public static final String START_MESSAGE_ADMIN = "Это бот для рассылки, вы - админ. Чтобы отправить сообщение, нажмите на кнопу \"/newMessage\". Дождитесь подтверждения отправки. ";

    public static final String DEFAULT_MESSAGE_USERS = "Этот бот не принимает сообщения. ";

    public static final String NEW_MESSAGE_COMMAND_REPLY = "Отправьте сообщение для рассылки.";

    public static final String WRONG_COMMAND_REPLY = "Неправильно введена команда. ";

    public static final String MESSAGE_SENDED_REPLY = "Сообщение отправлено. ";

    public static final String MESSAGE_CONFIRM_REPLY = "Подтвердите отправку сообщения: ";

    public static String usersListMessage(List<AccountDto> accounts) {
        StringBuilder res = new StringBuilder("Список получателей: \n");
        for (AccountDto account : accounts) {
            res.append(String.format("@%s \n", account.getUserName()));
        }

        return res.toString();

    }
}
