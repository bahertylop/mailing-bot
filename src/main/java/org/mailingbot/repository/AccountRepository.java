package org.mailingbot.repository;

import org.mailingbot.dto.AccountDto;
import org.mailingbot.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByChatId(Long chatId);
}
