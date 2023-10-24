package com.theDrive.servise;

import com.theDrive.entity.Role;
import com.theDrive.entity.User;
import com.theDrive.repos.RoleRepo;
import com.theDrive.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findOneByLogin(username);
    }

    public Boolean checkUniqueLogin(String login) {
        return userRepo.findOneByLogin(login) == null;
    }

    public void addUser(String username, String password, String roleCode) {

        Role role = roleRepo.findOneByCode(roleCode);

        User user = new User(username, new BCryptPasswordEncoder().encode(password), role);

        userRepo.save(user);

    }
}
