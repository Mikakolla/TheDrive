package com.theDrive.repos;

import com.theDrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findAll();

    User findOneById(Long userId);

    User findOneByLogin(String login);
}
