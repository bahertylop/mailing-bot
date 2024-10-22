package org.mailingbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.mailingbot.dto.AccountDto;
import org.mailingbot.dto.AdminDto;
import org.mailingbot.model.Account;
import org.mailingbot.repository.AccountRepository;
import org.mailingbot.service.AccountService;
import org.mailingbot.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AdminService adminService;
    private final AccountRepository accountRepository;

    @Override
    public Optional<AccountDto> getAccountDtoByChatId(Long chatId) {
        return accountRepository.findByChatId(chatId).map(AccountDto::from);
    }

    @Override
    public List<AccountDto> getAllUsers() {
        List<Long> adminChatIds = adminService.getAdmins().stream().map(AdminDto::getChatId).toList();

        return accountRepository.findAll().stream()
                .filter(accountDto -> !adminChatIds.contains(accountDto.getChatId()))
                .map(AccountDto::from)
                .toList();
    }

    @Override
    public void registerNewAccount(Message message) {
        if (accountRepository.findByChatId(message.getChatId()).isEmpty()) {
            Account account = Account.builder()
                    .chatId(message.getChatId())
                    .userName(message.getFrom().getUserName())
                    .name(message.getFrom().getFirstName())
                    .startTime(LocalDateTime.now())
                    .build();
            accountRepository.save(account);
        }
    }
}
