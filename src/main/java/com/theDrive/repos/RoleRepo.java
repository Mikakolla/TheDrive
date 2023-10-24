package com.theDrive.repos;

import com.theDrive.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findOneByCode(String code);

}
