package com.courier.sgacourierapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "verification_codes")
public class VerificationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    @Column(name = "user_id")
    private Long userId;
    
    @Column(nullable = false)
    private LocalDateTime expiryTime;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}