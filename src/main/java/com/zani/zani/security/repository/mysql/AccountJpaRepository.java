package com.zani.zani.security.repository.mysql;

import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.repository.entity.mysql.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findBySerialIdAndProvider(String serialId, ESecurityProvider provider);
}
