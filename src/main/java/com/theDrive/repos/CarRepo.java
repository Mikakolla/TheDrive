package com.theDrive.repos;

import com.theDrive.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepo extends JpaRepository<Car, Long> {

//    @Query("select cq from Car cq where cq.id = :carId")
    Car findOneById(Long carId);
}
