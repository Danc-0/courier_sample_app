package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationEntity, Long> {
    Optional<VerificationEntity> findByUserId(long username);
    void deleteByUserId(long username);
}