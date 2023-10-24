package com.theDrive.controller;

import com.theDrive.entity.User;
import com.theDrive.servise.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/user/{id}")
    @ResponseBody
    public void changeSubscriptionUser(@PathVariable("id") Long userId,
                                 Authentication auth) {

        User user = (User) auth.getPrincipal();

        subscriptionService.changeSubscriptionUser(userId, user.getId());
    }

    @PostMapping("/car/{id}")
    @ResponseBody
    public void changeSubscriptionCar(@PathVariable("id") Long carId,
                                       Authentication auth) {

        User user = (User) auth.getPrincipal();

        subscriptionService.changeSubscriptionCar(carId, user.getId());
    }

}
