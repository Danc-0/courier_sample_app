package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationEntity, Long> {
    Optional<VerificationEntity> findByUserEntity_Email(String username);
    Optional<VerificationEntity> findByUserEntity_IdNumber(String username);
    Optional<VerificationEntity> findByUserEntity_PhoneNumber(String username);
    void deleteByUserEntity_IdNumber(String username);
}