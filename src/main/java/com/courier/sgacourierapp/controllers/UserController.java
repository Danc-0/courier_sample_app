package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("internal/")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/admin/users")
    public String showUsersPage(final Model model) {
        try {
            List<UserEntity> users = usersService.getAllUsers();
            model.addAttribute("users", users);
            model.addAttribute("user", new UserEntity());  // For "Add User"
            model.addAttribute("showModal", false); // Default is false
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong. Please try again.");
        }

        return "users";
    }

    @GetMapping("/admin/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("showModal", true);  // Show the modal
        model.addAttribute("users", usersService.getAllUsers());
        return "users";
    }

    @GetMapping("/admin/users/edit/{phoneNumber}")
    public String editUser(@PathVariable String phoneNumber, Model model) {
        UserEntity user = usersService.getUserByPhoneNumber(phoneNumber);  // Fetch user by ID
        model.addAttribute("user", user);
        model.addAttribute("showModal", true);  // Show the modal
        model.addAttribute("users", usersService.getAllUsers());
        return "users";
    }

    @PostMapping("/admin/users/save")
    public String saveUser(@ModelAttribute UserEntity user, Model model) {
        try {
            usersService.addUser(user);
            model.addAttribute("successMessage", "User saved successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving user.");
        }
        return "redirect:/internal/admin/users";
    }

}
