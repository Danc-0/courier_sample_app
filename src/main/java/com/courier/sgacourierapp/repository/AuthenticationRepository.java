package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String username);
    Optional<UserEntity> findByEmail(String username);
    Optional<UserEntity> findByIdNumber(String username);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.isVerified = 1, u.isActivated = 1 WHERE u.id = :userId")
    void updateVerificationAndActivationToTrue(@Param("userId") Long userId);
}
