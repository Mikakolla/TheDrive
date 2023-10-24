package com.theDrive.controller;

import com.theDrive.pagination.PagingService;
import com.theDrive.servise.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private PagingService pagingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRegistrationPage() throws Exception {

        //given
        String page = "registration";

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/registration"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("registration")));
    }

    @Test
    void addUser() {
    }
}