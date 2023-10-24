package com.theDrive.servise;

import com.theDrive.entity.Car;
import com.theDrive.entity.Subscription;
import com.theDrive.entity.User;
import com.theDrive.entity.sub.*;
import com.theDrive.repos.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private BrandRepo mockBrandRepo;

    @Mock
    private ModelRepo mockModelRepo;

    @Mock
    private GenerationRepo mockGenerationRepo;

    @Mock
    private BodyRepo mockBodyRepo;

    @Mock
    private EngineRepo mockEngineRepo;

    @Mock
    private DriveRepo mockDriveRepo;

    @Mock
    private CarRepo mockCarRepo;

    @Value("${upload.dir}")
    private String url = "test";

    @Test
    void createCar() throws IOException {

        //given
        Long brandId = 1L;
        Brand brand = new Brand();
        Mockito.when(mockBrandRepo.findOneById(brandId)).thenReturn(brand);

        Long modelId = 1L;
        Model model = new Model();
        Mockito.when(mockModelRepo.findOneById(modelId)).thenReturn(model);

        Long generationId = 1L;
        Generation generation = new Generation();
        Mockito.when(mockGenerationRepo.findOneById(generationId)).thenReturn(generation);

        Long bodyId = 1L;
        Body body = new Body();
        Mockito.when(mockBodyRepo.findOneById(bodyId)).thenReturn(body);

        Long engineId = 1L;
        Engine engine = new Engine();
        Mockito.when(mockEngineRepo.findOneById(engineId)).thenReturn(engine);

        Long driveId = 1L;
        Drive drive = new Drive();
        Mockito.when(mockDriveRepo.findOneById(driveId)).thenReturn(drive);

        Integer yearCreate = 2005;
        Double engineSize = 2.0;
        Boolean transmission = true;
        String nickName = "";
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        User user = new User();
        user.setId(1L);

        ArgumentCaptor<Car> carCaptor = ArgumentCaptor.forClass(Car.class);

        Car car = new Car(brand, model, generation, yearCreate, engineSize, transmission, user, body, engine, drive, file.getOriginalFilename(), nickName);

        CarService carService = new CarService(mockBrandRepo, mockModelRepo, mockGenerationRepo, mockBodyRepo, mockEngineRepo, mockDriveRepo, mockCarRepo);
        carService.setUrl("test");

        //when
        carService.createCar(brandId, modelId, generationId, yearCreate, engineSize, transmission, bodyId, engineId, driveId, nickName, file, user);

        Mockito.verify(mockCarRepo).save(carCaptor.capture());

        //then
        Car result = carCaptor.getValue();

        Assertions.assertEquals(car.getBrand(), result.getBrand());

    }

}
