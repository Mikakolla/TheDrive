package com.theDrive.controller;

import com.theDrive.entity.CategoryServices;
import com.theDrive.entity.Company;
import com.theDrive.entity.Services;
import com.theDrive.entity.UseServices;
import com.theDrive.repos.CategoryServicesRepo;
import com.theDrive.repos.CompanyRepo;
import com.theDrive.repos.ServicesRepo;
import com.theDrive.repos.UseServicesRepo;
import com.theDrive.servise.UseServicesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    private final CompanyRepo companyRepo;
    private final CategoryServicesRepo categoryServicesRepo;
    private final ServicesRepo servicesRepo;
    private final UseServicesService useServicesService;

    @PostMapping("/add")
    public String addService(@RequestParam(name = "companyId") Long companyId,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "hourSpend") Integer hourSpend,
                             @RequestParam(name = "categoryId") Long categoryId,
                             @RequestParam(name = "price") Double price) {

        Company company = companyRepo.findOneById(companyId);
        CategoryServices categoryService = categoryServicesRepo.findOneById(categoryId);

        Services service = new Services(name, company, hourSpend, price, categoryService);

        servicesRepo.save(service);

        return "redirect:/profile";
    }

    @PostMapping("/edit")
    public String editService(@RequestParam(name = "serviceId") Long serviceId,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "hourSpend") Integer hourSpend,
                             @RequestParam(name = "categoryIdEdit") Long categoryId,
                             @RequestParam(name = "price") Double price) {

        Services serviceForEdit = servicesRepo.findOneById(serviceId);

        serviceForEdit.setName(name);
        serviceForEdit.setTimeSpend(hourSpend);
        serviceForEdit.setCategory(categoryServicesRepo.findOneById(categoryId));
        serviceForEdit.setPrice(price);

        servicesRepo.save(serviceForEdit);

        return "redirect:/profile";
    }

    @PostMapping("/delete")
    @ResponseBody
    public void deleteService(@RequestParam(name = "serviceId") Long serviceId) {

        Services service = servicesRepo.findOneById(serviceId);

        servicesRepo.delete(service);

    }

    @GetMapping("/free_time")
    @ResponseBody
    public List<Integer> getFreeTime(@RequestParam(name = "date") String date,
                            @RequestParam(name = "serviceId") Long serviceId) {

        return useServicesService.getFreeTime(date, serviceId);
    }
}
