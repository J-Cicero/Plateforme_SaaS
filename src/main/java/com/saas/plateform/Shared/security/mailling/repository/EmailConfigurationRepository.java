package com.saas.plateform.Shared.security.mailling.repository;

import com.saas.plateform.Shared.security.mailling.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, Long> {
    EmailConfiguration findFirstByOrderByIdAsc();
}
