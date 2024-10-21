package org.mailingbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mailingbot.model.Account;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    @NotNull(message = "id не может быть пустым")
    private Long id;

    @NotNull(message = "chatId не может быть пустым")
    private Long chatId;

    @NotBlank(message = "userName не может быть пустым")
    private String userName;

    @NotBlank(message = "name не может быть пустым")
    private String name;

    @NotNull(message = "startTime не может быть пустым")
    private LocalDateTime startTime;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .chatId(account.getChatId())
                .userName(account.getUserName())
                .name(account.getName())
                .startTime(account.getStartTime())
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts.stream().map(AccountDto::from).toList();
    }
}
