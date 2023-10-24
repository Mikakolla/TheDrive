package com.theDrive.controller;

import com.theDrive.entity.User;
import com.theDrive.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/add")
    public String addUser(User user, @RequestParam String roleCode, Model model) {

        if (user.getUsername() == "" || user.getPassword() == "") {
            model.addAttribute("message", "Пароль или логин не указаны");
            return "registration";
        }

        if (!userService.checkUniqueLogin(user.getLogin())) {
            model.addAttribute("message", "Такой логин уже используется");
            return "registration";
        } else {
            userService.addUser(user.getLogin(), user.getPassword(), roleCode);
        }

        return "redirect:/";

    }
}
