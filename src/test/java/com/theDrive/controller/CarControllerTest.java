package com.theDrive.controller;

import com.theDrive.entity.Role;
import com.theDrive.entity.User;
import com.theDrive.pagination.PagingService;
import com.theDrive.servise.CarService;
import com.theDrive.servise.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(CarController.class)
@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @MockBean
    private CarService carService;

    @Mock
    private Authentication auth;

    @MockBean
    private UserService userService;

    @MockBean
    private PagingService pagingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createCar() throws Exception {

        //given
        Long brandId = 1L;
        Boolean transmission = false;

        User user = new User("login", "pas", new Role(1L, "DRIVER"));
        user.setId(1L);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/car/create")
                .param("brand", String.valueOf(brandId))
                .with(csrf())
                .with(user(user)));

        //then
        Mockito.verify(carService, Mockito.times(1)).createCar(brandId, null, null, null, null, transmission, null, null, null, null, null, user);

    }
}