package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.VerificationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationEntity, Long> {
    Optional<VerificationEntity> findByUserId(Long username);

    @Modifying
    @Transactional
    void deleteByUserId(Long username);
}