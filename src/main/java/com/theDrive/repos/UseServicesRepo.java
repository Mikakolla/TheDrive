package com.theDrive.repos;

import com.theDrive.entity.UseServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UseServicesRepo extends JpaRepository<UseServices, Long> {


    @Query(value = "select us.* from use_services us where to_char(us.date_start, 'DD.MM.YYYY') = :date order by us.date_start", nativeQuery = true)
    List<UseServices> findAllByDate(String date);

    @Query("select us from UseServices us where us.services.company.id = :companyId order by us.dateStart desc")
    List<UseServices> findAllByCompanyId(Long companyId);

    UseServices findOneById(Long useServicesId);

    @Query("select us from UseServices us where us.car.id = :carId order by us.dateStart desc")
    List<UseServices> findAllByCarId(Long carId);
}
