package com.theDrive.servise;

import com.theDrive.entity.Role;
import com.theDrive.entity.User;
import com.theDrive.repos.RoleRepo;
import com.theDrive.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo mockUserRepo;

    @Mock
    private RoleRepo mockRoleRepo;

    @Test
    void loadUserByUsername() {

        //given
        String login = "driver";
        User user = User.builder().id(1L).build();
        Mockito.when(mockUserRepo.findOneByLogin("driver")).thenReturn(user);
        UserService userService = new UserService(mockUserRepo, null);

        //when
        UserDetails userResult = userService.loadUserByUsername(login);

        //then
        Assertions.assertNotNull(userResult);

    }

    @Test
    void checkUniqueLoginIfLoginExist() {

        //given
        String login = "driver";
        User userFromDB = User.builder()
                .login("driver")
                .build();

        UserService userService = new UserService(mockUserRepo, null);
        Mockito.when(mockUserRepo.findOneByLogin(login)).thenReturn(userFromDB);

        //when

        Boolean result = userService.checkUniqueLogin(login);

        //then
        Assertions.assertEquals(result, false);

    }

    @Test
    void checkUniqueLoginIfLoginNotExist() {

        //given
        String login = "driver";
        UserService userService = new UserService(mockUserRepo, null);
        Mockito.when(mockUserRepo.findOneByLogin(login)).thenReturn(null);

        //when

        Boolean result = userService.checkUniqueLogin(login);

        //then
        Assertions.assertEquals(result, true);

    }

    @Test
    void addUser() {

        //given
        String login = "driver";
        String password = "password";
        String roleCode = "DRIVER";

        UserService userService = new UserService(mockUserRepo, mockRoleRepo);

        Mockito.when(mockRoleRepo.findOneByCode("DRIVER")).thenReturn(new Role());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //when
        userService.addUser(login, password, roleCode);
        Mockito.verify(mockUserRepo).save(captor.capture());

        //then
        UserDetails userResult = captor.getValue();
        Assertions.assertEquals(userResult.getUsername(), login);

    }
}