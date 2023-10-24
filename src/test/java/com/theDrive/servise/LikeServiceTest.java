package com.theDrive.servise;

import com.theDrive.entity.Bookmark;
import com.theDrive.entity.Like;
import com.theDrive.entity.Role;
import com.theDrive.entity.User;
import com.theDrive.repos.LikeRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikeRepo mockLikeRepo;

    @Mock
    private Authentication auth;

    @Test
    void doesPostHaveLikeFromUser_return_true() {

        //given
        Long postId = 1L;
        User user = new User("test", "test", new Role(1L, "DRIVER"));
        Mockito.when(auth.getPrincipal()).thenReturn(user);
        Like like = new Like();
        Mockito.when(mockLikeRepo.findOneByPostIdAndUserId(postId, user.getId())).thenReturn(like);
        LikeService likeService = new LikeService(mockLikeRepo);

        //when
        Boolean result = likeService.doesPostHaveLikeFromUser(postId, auth);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void doesPostHaveLikeFromUser_return_false() {

        //given
        Long postId = 1L;
        User user = new User("test", "test", new Role(1L, "DRIVER"));
        Mockito.when(auth.getPrincipal()).thenReturn(user);
        Mockito.when(mockLikeRepo.findOneByPostIdAndUserId(postId, user.getId())).thenReturn(null);
        LikeService likeService = new LikeService(mockLikeRepo);

        //when
        Boolean result = likeService.doesPostHaveLikeFromUser(postId, auth);

        //then
        Assertions.assertFalse(result);
    }
}
