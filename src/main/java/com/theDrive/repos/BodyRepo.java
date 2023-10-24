package com.theDrive.repos;

import com.theDrive.entity.sub.Body;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyRepo extends JpaRepository<Body, Long> {

    Body findOneById(Long bodyId);

    List<Body> findAll();

}
