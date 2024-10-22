package org.mailingbot.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mailingbot.dto.AdminDto;
import org.mailingbot.model.Admin;
import org.mailingbot.repository.AdminRepository;
import org.mailingbot.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Optional<AdminDto> getAdminDtoByChatId(Long chatId) {
        return adminRepository.getAdminByChatId(chatId).map(AdminDto::from);
    }

    @Override
    public List<AdminDto> getAdmins() {
        return adminRepository.findAll().stream().map(AdminDto::from).toList();
    }

    @Override
    public void changeStage(Long chatId, Admin.BotStage stage) {
        Admin admin = getAdminOrThrow(chatId);
        admin.setBotStage(stage);
        saveAdmin(admin);
    }

    @Override
    public void saveNewMessage(Long chatId, Integer messageId) {
        Admin admin = getAdminOrThrow(chatId);
        admin.setMessageId(messageId);
        saveAdmin(admin);
    }

    private Admin getAdminOrThrow(Long chatId) {
        return adminRepository.getAdminByChatId(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Admin с chatId " + chatId + " не найден"));
    }

    private void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}
