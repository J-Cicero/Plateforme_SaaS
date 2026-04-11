package com.saas.plateform.Shared.security.mailling.repository;

import com.saas.plateform.Shared.security.mailling.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendRepository extends JpaRepository<Email, Long> {}
