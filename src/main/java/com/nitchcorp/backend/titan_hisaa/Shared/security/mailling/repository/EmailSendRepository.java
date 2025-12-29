package com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.repository;

import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendRepository extends JpaRepository<Email, Long> {}
