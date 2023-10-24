package com.theDrive.servise;

import com.theDrive.entity.sub.Brand;
import com.theDrive.repos.BrandRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepo mockBrandRepo;

    @Test
    void getAllBrand() {

        //given
        List<Brand> brands = new ArrayList<>();
        Mockito.when(mockBrandRepo.findAll()).thenReturn(brands);
        BrandService brandService = new BrandService(mockBrandRepo);

        //when
        List<Brand> result = brandService.getAllBrand();

        //then
        Assertions.assertNotNull(result);
    }
}
