package org.mailingbot.service;

import org.mailingbot.dto.AccountDto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> getAccountDtoByChatId(Long chatId);

    List<AccountDto> getAllUsers();

    void registerNewAccount(Message message);
}
