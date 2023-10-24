package com.theDrive.servise;

import com.theDrive.entity.sub.Brand;
import com.theDrive.repos.BrandRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {

    private final BrandRepo brandRepo;

    public List<Brand> getAllBrand() {
        return brandRepo.findAll();
    }
}
