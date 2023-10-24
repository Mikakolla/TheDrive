package com.theDrive.repos;

import com.theDrive.entity.sub.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineRepo extends JpaRepository<Engine, Long> {

    Engine findOneById(Long engineId);

    List<Engine> findAll();

}
