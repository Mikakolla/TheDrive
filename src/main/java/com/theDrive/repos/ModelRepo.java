package com.theDrive.repos;

import com.theDrive.entity.sub.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepo extends JpaRepository<Model, Long> {

    @Query("select m from Model m where m.brand.id = :brandId order by description")
    List<Model> findAllByBrand(Long brandId);

    Model findOneById(Long modelId);
}
