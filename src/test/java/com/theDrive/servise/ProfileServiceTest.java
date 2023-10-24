package com.theDrive.servise;

import com.theDrive.entity.*;
import com.theDrive.repos.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private UserRepo mockUserRepo;

    @Mock
    private CategoryServicesRepo mockCategoryServicesRepo;

    @Mock
    private BrandRepo mockBrandRepo;

    @Mock
    private BodyRepo mockBodyRepo;

    @Mock
    private EngineRepo engineRepo;

    @Mock
    private DriveRepo driveRepo;

    @Mock
    private CompanyRepo mockCompanyRepo;

    @Mock
    private UseServicesService mockUseServicesService;

    @Mock
    private Authentication auth;

    @Mock
    private SubscriptionService mockSubscriptionService;

    @Test
    void getAllProfile_DRIVER() {

        //given
        Long userId = 1L;
        Car car = new Car();
        User user = new User(null, null, new Role(null, "DRIVER"));
        user.setCars(Arrays.asList(car));
        Mockito.when(mockUserRepo.findOneById(userId)).thenReturn(user);
        List<CategoryServices> categoryServices = new ArrayList<>();
        Mockito.when(mockCategoryServicesRepo.findAll()).thenReturn(categoryServices);

        //when
        HashMap<String, Object> mapResult = profileService.getAllProfile(userId);

        //then
        Assertions.assertEquals(user.getCars(), mapResult.get("cars"));
        Assertions.assertEquals(categoryServices, mapResult.get("categories"));
    }

    @Test
    void getAllProfile_COMPANY() {

        //given
        Long userId = 1L;
        User user = new User(null, null, new Role(null, "COMPANY"));
        Mockito.when(mockUserRepo.findOneById(userId)).thenReturn(user);
        Company company = new Company();
        company.setId(1L);
        Mockito.when(mockCompanyRepo.findOneByUserId(userId)).thenReturn(company);
        List<UseServices> useServices = new ArrayList<>();
        Mockito.when(mockUseServicesService.getAllByCompanyId(company.getId())).thenReturn(useServices);
        List<CategoryServices> categoryServices = new ArrayList<>();
        Mockito.when(mockCategoryServicesRepo.findAll()).thenReturn(categoryServices);

        //when
        HashMap<String, Object> mapResult = profileService.getAllProfile(userId);

        //then
        Assertions.assertEquals(company, mapResult.get("company"));
        Assertions.assertEquals(useServices, mapResult.get("useServices"));
        Assertions.assertEquals(categoryServices, mapResult.get("categories"));
    }

    @Test
    void getProfile_DRIVER() {

        //given
        Long userId = 1L;
        Car car = new Car();
        User user = new User(null, null, new Role(null, "DRIVER"));
        user.setCars(Arrays.asList(car));
        Mockito.when(mockUserRepo.findOneById(userId)).thenReturn(user);
        Mockito.when(mockSubscriptionService.getSubscriptionOnUser(userId, auth)).thenReturn(true);

        //when
        HashMap<String, Object> mapResult = profileService.getProfile(userId, auth);

        //then
        Assertions.assertEquals(user.getRole().getCode(), mapResult.get("role"));
        Assertions.assertEquals(user.getCars(), mapResult.get("cars"));
        Assertions.assertEquals(user, mapResult.get("user"));
    }

    @Test
    void getProfile_COMPANY() {

        //given
        Long userId = 1L;
        Car car = new Car();
        User user = new User(null, null, new Role(null, "COMPANY"));
        user.setId(1L);
        user.setCars(Arrays.asList(car));
        Mockito.when(mockUserRepo.findOneById(userId)).thenReturn(user);

        Mockito.when(auth.getPrincipal()).thenReturn(user);

        Company company = new Company();
        Mockito.when(mockCompanyRepo.findOneByUserId(userId)).thenReturn(company);

        //when
        HashMap<String, Object> mapResult = profileService.getProfile(userId, auth);

        //then
        Assertions.assertEquals(user.getRole().getCode(), mapResult.get("role"));
        Assertions.assertEquals(user.getCars(), mapResult.get("cars"));
        Assertions.assertEquals(company, mapResult.get("company"));
        Assertions.assertEquals(user, mapResult.get("user"));
    }
}