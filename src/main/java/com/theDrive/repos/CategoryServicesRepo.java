package com.theDrive.repos;

import com.theDrive.entity.CategoryServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryServicesRepo extends JpaRepository<CategoryServices, Long> {

    List<CategoryServices> findAll();

    CategoryServices findOneById(Long categoryId);
}
