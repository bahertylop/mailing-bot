package org.mailingbot.util;

import org.mailingbot.dto.AccountDto;

import java.util.List;

public class ReplyConstants {

    public static final String START_MESSAGE_USER = "Скупаю ваши личные кабинеты Тинькофф\n" +
            "Можно получить каждому пользователю банка от 14 лет.\n" +
            "\n" +
            "‼\uFE0F- Не должно быть премиума в течении последних 180 дней\n" +
            "‼\uFE0F- Карта должна быть физической (пластик), а не виртуальной\n" +
            "‼\uFE0F- Джуниор не подходит \n" +
            "\n" +
            "\n" +
            "Нужно перейти по ссылке и оформить заявку на премиум - https://www.tbank.ru/baf/3a1mCE4CLZD\n" +
            "\n" +
            "После оформления в течении 2-3 дней в вашем личном кабинете Тинькофф около аватарки появится плашка “premium”, как только она появилась - пишете мне номер, и говорите код, который я вам отправляю от Every Lounge (В сам банк НЕ вхожу, а только в приложение по проходам)\n" +
            "\n" +
            "Выплачиваю после того, как продам ваши лк - обычно в течении суток. После выплаты можете писать в поддержку о том, чтобы вам отключили премиум.\n" +
            "\n" +
            "Оплата: 250\n" +
            "При объеме могу повысить сумму, обговаривается индивидуально.\n" +
            "\n" +
            "Связь: @chaoqq";

    public static final String START_MESSAGE_ADMIN = "Это бот для рассылки, вы - админ. Чтобы отправить сообщение, нажмите на кнопу \"/newMessage\". Дождитесь подтверждения отправки. ";

    public static final String DEFAULT_MESSAGE_USERS = "Этот бот не принимает сообщения. ";

    public static final String NEW_MESSAGE_COMMAND_REPLY = "Отправьте сообщение для рассылки.";

    public static final String WRONG_COMMAND_REPLY = "Неправильно введена команда. ";

    public static final String MESSAGE_SENDED_REPLY = "Сообщение отправлено. ";

    public static final String MESSAGE_NOT_SENDED_REPLY = "Не получилось отправить сообщение. ";

    public static final String MESSAGE_CONFIRM_REPLY = "Подтвердите отправку сообщения: ";


    public static String usersListMessage(List<AccountDto> accounts) {
        StringBuilder res = new StringBuilder("Список получателей: \n");
        for (AccountDto account : accounts) {
            res.append(String.format("@%s \n", account.getUserName()));
        }

        return res.toString();

    }
}
