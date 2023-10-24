package com.theDrive.controller;

import com.theDrive.entity.Role;
import com.theDrive.entity.User;
import com.theDrive.servise.SubscriptionService;
import com.theDrive.servise.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void changeSubscriptionUser() throws Exception {

        //given
        Long userId = 1L;
        User user = new User("login", "pas", new Role(1L, "DRIVER"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/subscription/user/{id}", "1")
                .with(csrf())
                .with(user(user)));

        //then
        Mockito.verify(subscriptionService, Mockito.times(1)).changeSubscriptionUser(userId, user.getId());

    }

    @Test
    void changeSubscriptionCar() throws Exception {

        //given
        Long carId = 1L;
        User user = new User("login", "pas", new Role(1L, "DRIVER"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/subscription/car/{id}", "1")
                .with(csrf())
                .with(user(user)));

        //then
        Mockito.verify(subscriptionService, Mockito.times(1)).changeSubscriptionCar(carId, user.getId());
    }
}