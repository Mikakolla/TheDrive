package com.theDrive.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theDrive.entity.sub.Generation;
import com.theDrive.entity.sub.Model;
import com.theDrive.pagination.PagingService;
import com.theDrive.repos.GenerationRepo;
import com.theDrive.repos.ModelRepo;
import com.theDrive.servise.PostService;
import com.theDrive.servise.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @MockBean
    private PagingService pagingService;

    @MockBean
    private ModelRepo modelRepo;

    @MockBean
    private GenerationRepo generationRepo;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void getModelsByBrand() throws Exception {

        //given
        Long brandId = 1L;
        Model model = new Model(1L, null, null, null);
        List<Model> models = Arrays.asList(model);
        Mockito.when(modelRepo.findAllByBrand(brandId)).thenReturn(models);

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/news/models")
                .param("brandId", String.valueOf(brandId)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)));
    }

    @Test
    void getGenerationsByModel() throws Exception {

        //given
        Long modelId = 1L;
        Generation generation = new Generation(1L, null, null, null, null, null);
        List<Generation> generations = Arrays.asList(generation);
        Mockito.when(generationRepo.findAllByModelId(modelId)).thenReturn(generations);

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/news/generation")
                .param("modelId", String.valueOf(modelId)));

        //then
        System.out.println();
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)));
    }
}