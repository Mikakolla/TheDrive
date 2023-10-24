package com.theDrive.servise;

import com.theDrive.entity.Bookmark;
import com.theDrive.entity.Role;
import com.theDrive.entity.User;
import com.theDrive.repos.BookmarkRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {

    @Mock
    private BookmarkRepo mockBookmarkRepo;

    @Mock
    private Authentication auth;

    @Test
    void doesPostHaveBookmarkFromUser_return_true() {

        //given
        Long postId = 1L;
        User user = new User("test", "test", new Role(1L, "DRIVER"));
        Mockito.when(auth.getPrincipal()).thenReturn(user);
        Bookmark bookmark = new Bookmark();
        Mockito.when(mockBookmarkRepo.findOneByPostIdAndUserId(postId, user.getId())).thenReturn(bookmark);
        BookmarkService bookmarkService = new BookmarkService(mockBookmarkRepo);

        //when
        Boolean result = bookmarkService.doesPostHaveBookmarkFromUser(postId, auth);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    void doesPostHaveBookmarkFromUser_return_false() {

        //given
        Long postId = 1L;
        User user = new User("test", "test", new Role(1L, "DRIVER"));
        Mockito.when(auth.getPrincipal()).thenReturn(user);
        Mockito.when(mockBookmarkRepo.findOneByPostIdAndUserId(postId, user.getId())).thenReturn(null);
        BookmarkService bookmarkService = new BookmarkService(mockBookmarkRepo);

        //when
        Boolean result = bookmarkService.doesPostHaveBookmarkFromUser(postId, auth);

        //then
        Assertions.assertFalse(result);
    }
}
