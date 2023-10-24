package com.theDrive.controller;

import com.theDrive.servise.UseServicesService;
import com.theDrive.servise.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UseServicesController.class)
class UseServicesControllerTest {

    @MockBean
    private UseServicesService useServicesService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addUseServices() throws Exception {

        //given
        Long serviceId = 1L;
        String date = "date";
        String time = "time";
        Long carId = 1L;

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/use_services/add")
                .param("serviceId", String.valueOf(serviceId))
                .param("date", date)
                .param("time", time)
                .param("carId", String.valueOf(carId))
                .with(csrf()));

        //then
        Mockito.verify(useServicesService, Mockito.times(1)).add(serviceId, date, time, carId);

    }

    @Test
    void changeStatus() throws Exception {

        //given
        Long useServiceId = 1L;
        String status = "status";

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/use_services/change")
                .param("useServiceId", String.valueOf(useServiceId))
                .param("status", status)
                .with(csrf()));

        //then
        Mockito.verify(useServicesService, Mockito.times(1)).changeStatus(useServiceId, status);

    }
}