package org.mailingbot.service;

import org.mailingbot.dto.AdminDto;
import org.mailingbot.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Optional<AdminDto> getAdminDtoByChatId(Long chatId);

    List<AdminDto> getAdmins();

    void changeStage(Long chatId, Admin.BotStage stage);

    void saveNewMessage(Long chatId, String message);
}
