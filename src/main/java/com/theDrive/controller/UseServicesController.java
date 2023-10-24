package com.theDrive.controller;

import com.theDrive.servise.UseServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/use_services")
public class UseServicesController {

    private final UseServicesService useServicesService;

    @PostMapping("/add")
    public String addUseServices(@RequestParam(name = "serviceId") Long serviceId,
                               @RequestParam(name = "date") String date,
                               @RequestParam(name = "time") String time,
                               @RequestParam(name = "carId") Long carId) throws ParseException {

        String profileId = useServicesService.add(serviceId, date, time, carId);

        return "redirect:/profile/" + profileId;

    }

    @PostMapping("/change")
    @ResponseBody
    public void changeStatus(@RequestParam(name = "useServiceId") Long useServiceId,
                             @RequestParam(name = "status") String status) {

        useServicesService.changeStatus(useServiceId, status);

    }
}
