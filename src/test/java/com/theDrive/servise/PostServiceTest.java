package com.theDrive.servise;

import com.theDrive.entity.CategoryServices;
import com.theDrive.entity.Post;
import com.theDrive.entity.sub.Brand;
import com.theDrive.entity.sub.Model;
import com.theDrive.repos.BrandRepo;
import com.theDrive.repos.CategoryServicesRepo;
import com.theDrive.repos.ModelRepo;
import com.theDrive.repos.PostRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepo mockPostRepo;

    @Mock
    private BrandRepo mockBrandRepo;

    @Mock
    private CategoryServicesRepo categoryServicesRepo;

    @Mock
    private ModelRepo mockModelRepo;

    @Test
    void getOneById() {

        //given
        Long postId = 1L;
        Post post = new Post();
        Mockito.when(mockPostRepo.findOneById(postId)).thenReturn(post);

        //when
        Post resultPost = postService.getOneById(postId);

        //then
        Assertions.assertEquals(post, resultPost);
    }

    @Test
    void dataForFilter() {

        //given
        Brand brand = new Brand();
        List<Brand> brands = Arrays.asList(brand);
        CategoryServices categoryService = new CategoryServices();
        List<CategoryServices> categoryServices = Arrays.asList(categoryService);
        Mockito.when(mockBrandRepo.findAll()).thenReturn(brands);
        Mockito.when(categoryServicesRepo.findAll()).thenReturn(categoryServices);

        //when
        HashMap<String, Object> mapResult = postService.dataForFilter();

        //then
        Assertions.assertEquals(brands, mapResult.get("brands"));
        Assertions.assertEquals(categoryServices, mapResult.get("categories"));
    }

    @Test
    void findAllModelByBrand() {

        //given
        Long brandId = 1L;
        Model model = new Model();
        List<Model> models = Arrays.asList(model);
        Mockito.when(mockModelRepo.findAllByBrand(brandId)).thenReturn(models);

        //when
        List<Model> modelsResult = postService.findAllModelByBrand(brandId);

        //then
        Assertions.assertEquals(models, modelsResult);
    }
}
