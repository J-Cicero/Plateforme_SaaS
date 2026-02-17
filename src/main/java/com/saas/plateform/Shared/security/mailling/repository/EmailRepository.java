package com.saas.plateform.Shared.security.mailling.repository;

import com.saas.plateform.Shared.security.mailling.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailConfiguration, Long> {
    EmailConfiguration findFirstByOrderByIdAsc();
}
