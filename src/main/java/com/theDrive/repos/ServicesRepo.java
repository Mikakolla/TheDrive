package com.theDrive.repos;

import com.theDrive.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


public interface ServicesRepo extends JpaRepository<Services, Long> {

    Services findOneById(Long serviceId);
}
