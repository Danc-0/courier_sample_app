package com.courier.sgacourierapp.handler;

import com.courier.sgacourierapp.services.EmailVerificationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final EmailVerificationService emailVerificationService;

    public LoginSuccessHandler(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();
        emailVerificationService.generateAndStoreVerificationCode(username);
        emailVerificationService.markUserForVerification(request.getSession());
        response.sendRedirect("/internal/verify");
    }
}