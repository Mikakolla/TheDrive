package com.theDrive.repos;

import com.theDrive.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepo extends JpaRepository<Company, Long> {

    Company findOneById(Long companyId);

    @Query("select c from Company c where c.user.id = :userId")
    Company findOneByUserId(Long userId);
}
