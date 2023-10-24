package com.theDrive.repos;

import com.theDrive.entity.sub.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepo extends JpaRepository<Brand, Long> {

    @Query("select b from Brand b order by description")
    List<Brand> findAll();

    Brand findOneById(Long brandId);
}
