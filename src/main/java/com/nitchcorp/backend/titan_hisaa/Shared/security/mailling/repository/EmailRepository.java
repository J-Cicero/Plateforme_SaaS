package com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.repository;

import com.nitchcorp.backend.titan_hisaa.Shared.security.mailling.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailConfiguration, Long> {
    EmailConfiguration findFirstByOrderByIdAsc();
}
