package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.mapper.CourierRequestMapper;
import com.courier.sgacourierapp.mapper.CourierResponseMapper;
import com.courier.sgacourierapp.models.request.LoginRequest;
import com.courier.sgacourierapp.models.request.VerifyRequest;
import com.courier.sgacourierapp.services.AuthenticationService;
import com.courier.sgacourierapp.services.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/internal")
public class AuthController {


    final CourierRequestMapper courierRequestMapper;

    final CourierResponseMapper courierResponseMapper;

    final AuthenticationService authenticationService;

    final EmailVerificationService emailVerificationService;

    public AuthController(CourierRequestMapper courierRequestMapper, CourierResponseMapper courierResponseMapper, AuthenticationService authenticationService, EmailVerificationService emailVerificationService) {
        this.courierRequestMapper = courierRequestMapper;
        this.courierResponseMapper = courierResponseMapper;
        this.authenticationService = authenticationService;
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("/login")
    public String showLoginForm(final Model model) {
        final LoginRequest loginRequest = new LoginRequest();
        model.addAttribute(loginRequest);
        return "login";
    }

    @GetMapping("/verify")
    public String showVerificationForm(Model model) {
        final VerifyRequest verifyRequest = new VerifyRequest();
        model.addAttribute(verifyRequest);
        return "verification";
    }

    @PostMapping("/verify")
    public String verifyUser(@RequestParam("verificationCode") String code, Authentication authentication) {
        String email = authentication.getName();
        boolean isVerified = emailVerificationService.verifyCode(email, code);

        if (isVerified) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/verification?error=true";
        }
    }

}
