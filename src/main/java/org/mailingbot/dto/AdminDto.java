package org.mailingbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mailingbot.model.Admin;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {

    @NotNull(message = "id не может быть пустым")
    private Long id;

    @NotNull(message = "chatId не может быть пустым")
    private Long chatId;

    @NotNull(message = "botStage не может быть пустым")
    private Admin.BotStage botStage;

    @NotBlank(message = "message не может быть пустым")
    private String message;

    public static AdminDto from(Admin admin) {
        return AdminDto.builder()
                .id(admin.getId())
                .chatId(admin.getChatId())
                .botStage(admin.getBotStage())
                .message(admin.getMessage())
                .build();
    }

    public static List<AdminDto> from(List<Admin> admins) {
        return admins.stream().map(AdminDto::from).toList();
    }
}
