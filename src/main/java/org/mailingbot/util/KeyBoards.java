package org.mailingbot.util;

import org.mailingbot.model.Admin;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyBoards {

    public static ReplyKeyboardMarkup chooseKeyboardByStage(Admin.BotStage stage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        if (stage.equals(Admin.BotStage.DEFAULT)) {
            row1.add(new KeyboardButton("/newMessage"));
        } else if (stage.equals(Admin.BotStage.CONFIRM)) {
            row1.add(new KeyboardButton("/confirmMessage"));
        }

        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
