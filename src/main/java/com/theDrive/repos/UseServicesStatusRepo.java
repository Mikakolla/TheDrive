package com.theDrive.repos;

import com.theDrive.entity.sub.UseServicesStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseServicesStatusRepo extends JpaRepository<UseServicesStatus, Long> {

    UseServicesStatus findOneByCode(String code);

}
