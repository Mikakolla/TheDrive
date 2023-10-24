package com.theDrive.controller;

import com.theDrive.entity.User;
import com.theDrive.servise.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @PostMapping("/create")
    public String createCar(@RequestParam(name = "brand") Long brandId,
                          @RequestParam(name = "model", required = false) Long modelId,
                          @RequestParam(name = "generation", required = false) Long generationId,
                          @RequestParam(name = "yearCreate", required = false) Integer yearCreate,
                          @RequestParam(name = "engine_size", required = false) Double engineSize,
                          @RequestParam(name = "transmission", required = false) Boolean transmission,
                          @RequestParam(name = "body", required = false) Long bodyId,
                          @RequestParam(name = "engine", required = false) Long engineId,
                          @RequestParam(name = "drive", required = false) Long driveId,
                          @RequestParam(name = "nickName", required = false) String nickName,
                          @RequestParam(value = "image", required = false) MultipartFile file,
                          Authentication auth) throws IOException {

        User user = (User) auth.getPrincipal();

        if (transmission == null)
            transmission = false;

        carService.createCar(brandId, modelId, generationId, yearCreate, engineSize, transmission, bodyId, engineId, driveId, nickName, file, user);

        return "redirect:/profile";

    }

    @PostMapping("/delete")
    public String deleteCar(@RequestParam(name = "carId") Long carId) {

        carService.deleteCar(carId);

        return "redirect:/profile";
    }

    @PostMapping("/edit")
    public String editCar(@RequestParam(name = "carId") Long carId,
                          @RequestParam(name = "brand") Long brandId,
                          @RequestParam(name = "model", required = false) Long modelId,
                          @RequestParam(name = "generation", required = false) Long generationId,
                          @RequestParam(name = "yearCreate", required = false) Integer yearCreate,
                          @RequestParam(name = "engine_size", required = false) Double engineSize,
                          @RequestParam(name = "transmission", required = false) Boolean transmission,
                          @RequestParam(name = "body", required = false) Long bodyId,
                          @RequestParam(name = "engine", required = false) Long engineId,
                          @RequestParam(name = "drive", required = false) Long driveId,
                          @RequestParam(name = "nickName", required = false) String nickName) throws IOException {

        if (transmission == null)
            transmission = false;

        carService.editCar(carId, brandId, modelId, generationId, yearCreate, engineSize, transmission, bodyId, engineId, driveId, nickName);

        return "redirect:/profile/car_profile/" + carId;
    }
}
