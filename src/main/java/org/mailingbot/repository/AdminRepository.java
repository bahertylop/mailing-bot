package org.mailingbot.repository;

import org.mailingbot.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> getAdminByChatId(Long chatId);


}
