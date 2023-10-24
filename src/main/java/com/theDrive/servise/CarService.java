package com.theDrive.servise;

import com.theDrive.entity.Car;
import com.theDrive.entity.User;
import com.theDrive.entity.sub.*;
import com.theDrive.repos.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class CarService {

    private final BrandRepo brandRepo;
    private final ModelRepo modelRepo;
    private final GenerationRepo generationRepo;
    private final BodyRepo bodyRepo;
    private final EngineRepo engineRepo;
    private final DriveRepo driveRepo;
    private final CarRepo carRepo;

    @Value("${upload.dir}")
    private String url;


    public void createCar(Long brandId, Long modelId, Long generationId, Integer yearCreate, Double engineSize, Boolean transmission, Long bodyId,
                          Long engineId, Long driveId, String nickName, MultipartFile file, User user) throws IOException {

        Brand brand = brandRepo.findOneById(brandId);
        Model model = modelRepo.findOneById(modelId);
        Generation generation = generationRepo.findOneById(generationId);
        Body body = bodyRepo.findOneById(bodyId);
        Engine engine = engineRepo.findOneById(engineId);
        Drive drive = driveRepo.findOneById(driveId);

        UUID uuid = UUID.randomUUID();

        String pathToSave = url + "\\" + user.getId() + "\\" + uuid;

        Path pathToCreateFolder = Paths.get(pathToSave);

        File convertFile = new File(pathToSave + "\\" + file.getOriginalFilename());
        File newDirectory  = new File(convertFile, url);

        if (!newDirectory.exists()) {
            Files.createDirectories(pathToCreateFolder);
        }

        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();

        Car car = new Car(brand, model, generation, yearCreate, engineSize, transmission,  user, body, engine, drive, file.getOriginalFilename(), nickName);
        car.setUuid(uuid.toString());

        carRepo.save(car);

    }

    public void deleteCar(Long carId) {

        Car car = carRepo.findOneById(carId);

        carRepo.delete(car);

    }


    //TODO переделать
    public void editCar(Long carId, Long brandId, Long modelId, Long generationId, Integer yearCreate, Double engineSize, Boolean transmission, Long bodyId,
                          Long engineId, Long driveId, String nickName) throws IOException {

        Car car = carRepo.findOneById(carId);
        Brand brand = brandRepo.findOneById(brandId);
        Model model = modelRepo.findOneById(modelId);
        Generation generation = generationRepo.findOneById(generationId);
        Body body = bodyRepo.findOneById(bodyId);
        Engine engine = engineRepo.findOneById(engineId);
        Drive drive = driveRepo.findOneById(driveId);

        car.setBrand(brand);
        car.setModel(model);
        car.setGeneration(generation);
        car.setYearCreate(yearCreate);
        car.setEngineSize(engineSize);
        car.setTransmission(transmission);
        car.setBody(body);
        car.setEngine(engine);
        car.setDrive(drive);
        car.setName(nickName);

        carRepo.save(car);

    }
}
