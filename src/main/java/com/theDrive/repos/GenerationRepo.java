package com.theDrive.repos;

import com.theDrive.entity.sub.Generation;
import com.theDrive.entity.sub.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenerationRepo extends JpaRepository<Generation, Long> {

    @Query("select gen from Generation gen where gen.model.id = :modelId order by description")
    List<Generation> findAllByModelId(Long modelId);

    Generation findOneById(Long generationId);

}
