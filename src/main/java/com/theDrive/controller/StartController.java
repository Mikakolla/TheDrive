package com.theDrive.controller;

import com.theDrive.servise.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping()
public class StartController {

    private final BrandService brandService;

    @GetMapping
    public String getStartPage(Model model) {

        model.addAttribute("brands", brandService.getAllBrand());

        return "index";
    }
}
