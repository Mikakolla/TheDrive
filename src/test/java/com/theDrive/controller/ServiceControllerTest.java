package com.theDrive.controller;

import com.theDrive.entity.Services;
import com.theDrive.repos.CategoryServicesRepo;
import com.theDrive.repos.CompanyRepo;
import com.theDrive.repos.ServicesRepo;
import com.theDrive.servise.UseServicesService;
import com.theDrive.servise.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ServiceController.class)
class ServiceControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private CompanyRepo companyRepo;

    @MockBean
    private CategoryServicesRepo categoryServicesRepo;

    @MockBean
    private UseServicesService useServicesService;

    @MockBean
    private ServicesRepo servicesRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteService() throws Exception {

        //given
        Long serviceId = 1L;
        Services services = new Services();
        services.setId(1L);

        Mockito.when(servicesRepo.findOneById(serviceId)).thenReturn(services);

        ArgumentCaptor<Services> servicesCaptor = ArgumentCaptor.forClass(Services.class);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/service/delete")
                .param("serviceId", String.valueOf(serviceId))
                .with(csrf()));

        Mockito.verify(servicesRepo).delete(servicesCaptor.capture());

        //then
        Services result = servicesCaptor.getValue();
        Assertions.assertEquals(services.getId(), result.getId());
    }

    @Test
    void getFreeTime() throws Exception {

        //given
        String date = "date";
        Long serviceId = 1L;
        List<Integer> freeTime = Arrays.asList(1);

        Mockito.when(useServicesService.getFreeTime(date, serviceId)).thenReturn(freeTime);

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/service/free_time")
                .param("date", date)
                .param("serviceId", String.valueOf(serviceId))
                .with(csrf()));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", CoreMatchers.is(1)));
    }
}