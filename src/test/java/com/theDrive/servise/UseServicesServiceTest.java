package com.theDrive.servise;

import com.theDrive.entity.*;
import com.theDrive.entity.sub.UseServicesStatus;
import com.theDrive.repos.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UseServicesServiceTest {

    @InjectMocks
    private UseServicesService useServicesService;

    @Mock
    private CompanyRepo mockCompanyRepo;

    @Mock
    private ServicesRepo mockServicesRepo;

    @Mock
    private UseServicesRepo mockUseServicesRepo;

    @Mock
    private CarRepo mockCarRepo;

    @Mock
    private UseServicesStatusRepo mockUseServicesStatusRepo;

    @Test
    void getFreeTime() {

        //given
        Long serviceId = 1L;
        ScheduleWork scheduleWork = new ScheduleWork(1L, true, true, true, true, true, true, true, new Time(8l), new Time(20l));
        Company company = new Company();
        company.setId(1L);
        company.setScheduleWork(scheduleWork);
        Services services = new Services("", company, 1, 1.0, new CategoryServices());
        String date = (new Date()).toString();
        Mockito.when(mockServicesRepo.findOneById(serviceId)).thenReturn(services
        );
        Mockito.when(mockCompanyRepo.findOneById(serviceId)).thenReturn(company);

        List<UseServices> useServices = new ArrayList<>();
        Mockito.when(mockUseServicesRepo.findAllByDate(date)).thenReturn(useServices);

        Mockito.when(mockCompanyRepo.findOneById(services.getCompany().getId())).thenReturn(company);

        //when
        List<Integer> result = useServicesService.getFreeTime(date, serviceId);

        //then
        Assertions.assertNotNull(result);
    }

    @Test
    void add() throws ParseException {

        //given
        Long serviceId = 1L;
        User user = new User();
        user.setId(1L);
        Company company = new Company(null, null, null, null, null, null, null, null, user, null);
        Services services = new Services(null, company, 1, null, null);
        Mockito.when(mockServicesRepo.findOneById(serviceId)).thenReturn(services);

        String date = "12.12.2022";
        String time = "10:00";

        Long carId = 1L;
        Car car = new Car(null, null, null, null, null, null, user, null, null, null, null, null);
        Mockito.when(mockCarRepo.findOneById(carId)).thenReturn(car);

        UseServicesStatus status = new UseServicesStatus();
        Mockito.when(mockUseServicesStatusRepo.findOneByCode("AWAIT")).thenReturn(status);

        //when
        String result = useServicesService.add(serviceId, date, time, carId);

        //then
        Assertions.assertEquals(user.getId().toString(), result);
    }

    @Test
    void getAllByCompanyId() {

        //given
        Long companyId = 1L;
        List<UseServices> useServices = new ArrayList<>();
        Mockito.when(mockUseServicesRepo.findAllByCompanyId(companyId)).thenReturn(useServices);

        //when
        List<UseServices> result = useServicesService.getAllByCompanyId(companyId);

        //then
        Assertions.assertEquals(useServices, result);
    }

    @Test
    void getAllByCar() {

        //given
        Long carId = 1L;
        List<UseServices> useServices = new ArrayList<>();
        Mockito.when(mockUseServicesRepo.findAllByCarId(carId)).thenReturn(useServices);

        //when
        List<UseServices> result = useServicesService.getAllByCar(carId);

        //then
        Assertions.assertEquals(useServices, result);
    }

    @Test
    void changeStatus() {

        //given
        Long useServiceId = 1L;
        UseServices useServices = new UseServices();
        Mockito.when(mockUseServicesRepo.findOneById(useServiceId)).thenReturn(useServices);

        String statusStr = "";
        UseServicesStatus status = new UseServicesStatus();
        Mockito.when(mockUseServicesStatusRepo.findOneByCode(statusStr)).thenReturn(status);

        useServices.setStatus(status);

        ArgumentCaptor<UseServices> useServicesCaptor = ArgumentCaptor.forClass(UseServices.class);

        //when
        useServicesService.changeStatus(useServiceId, statusStr);

        Mockito.verify(mockUseServicesRepo).save(useServicesCaptor.capture());

        //then
        UseServices result = useServicesCaptor.getValue();

        Assertions.assertEquals(useServices, result);
    }
}