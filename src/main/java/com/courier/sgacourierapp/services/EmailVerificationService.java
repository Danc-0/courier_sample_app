package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.entities.VerificationEntity;
import com.courier.sgacourierapp.repository.AuthenticationRepository;
import com.courier.sgacourierapp.repository.VerificationCodeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailVerificationService {

    private static final String VERIFICATION_REQUIRED_SESSION_ATTRIBUTE = "verificationRequired";

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    final static int VERIFICATION_CODE_LENGTH = 6;

    final static int VERIFICATION_CODE_EXPIRY_TIME = 15;

    public void generateAndStoreVerificationCode(String username) {
        String code = generateRandomCode();

        UserEntity userEntity = authenticationRepository.findByEmail(username)
                .or(() -> authenticationRepository.findByIdNumber(username))
                .or(() -> authenticationRepository.findByPhoneNumber(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        verificationCodeRepository.deleteByUserEntity_IdNumber(userEntity.getIdNumber());

        VerificationEntity verificationCode = VerificationEntity.builder()
                .verificationCode(code)
                .userEntity(userEntity)
                .expiryTime(LocalDateTime.now().plusMinutes(VERIFICATION_CODE_EXPIRY_TIME))
                .build();

        verificationCodeRepository.save(verificationCode);

        emailService.sendVerificationEmail(userEntity.getEmail(), verificationCode.getVerificationCode());
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(VERIFICATION_CODE_LENGTH);
        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public boolean verifyCode(String email, String code) {
        Optional<VerificationEntity> verificationEntity = verificationCodeRepository
                .findByUserEntity_Email(email)
                .or(() -> verificationCodeRepository.findByUserEntity_IdNumber(email)
                        .or(() -> verificationCodeRepository.findByUserEntity_PhoneNumber(email)));

        if (verificationEntity.isEmpty()) {
            return false;
        }

        final VerificationEntity verificationCode = verificationEntity.get();

        boolean isValid = verificationCode.getVerificationCode().equals(code) && !verificationCode.isExpired();

        if (isValid) {
            verificationCodeRepository.delete(verificationCode);
        }

        return isValid;
    }

    public void markUserForVerification(HttpSession session) {
        session.setAttribute(VERIFICATION_REQUIRED_SESSION_ATTRIBUTE, true);
    }

    public boolean isVerificationRequired(HttpSession session) {
        Object attr = session.getAttribute(VERIFICATION_REQUIRED_SESSION_ATTRIBUTE);
        return attr != null && (Boolean) attr;
    }

    public void completeVerification(HttpSession session) {
        session.removeAttribute(VERIFICATION_REQUIRED_SESSION_ATTRIBUTE);
    }
}