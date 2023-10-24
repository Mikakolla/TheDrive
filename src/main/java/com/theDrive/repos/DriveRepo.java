package com.theDrive.repos;

import com.theDrive.entity.sub.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriveRepo extends JpaRepository<Drive, Long> {

    Drive findOneById(Long driveId);

    List<Drive> findAll();

}
