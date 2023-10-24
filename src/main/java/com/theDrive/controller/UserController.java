package com.theDrive.controller;

import com.theDrive.entity.User;
import com.theDrive.repos.UserRepo;
import com.theDrive.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;

    private final UserService userService;

    @PostMapping("/save")
    public String saveEditUser(@RequestParam(name = "login") String login,
                             @RequestParam(name = "password", required = false) String password,
                             @RequestParam(name = "firstName") String firstName,
                             @RequestParam(name = "lastName") String lastName,
                             Model model, Authentication auth) {

        User user = (User) auth.getPrincipal();

        user = userRepo.findOneById(user.getId());

        if (!userService.checkUniqueLogin(login) && !login.equals(user.getLogin())) {
            model.addAttribute("errorLogin", "Данный логин уже используется");
        } else {
            user.setLogin(login);
            if (password != null && !password.equals(""))
                user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);

            userRepo.save(user);
        }

        model.addAttribute("user", user);

        return "profile::userForm";
    }
}
