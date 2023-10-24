package com.theDrive.controller;

import com.theDrive.entity.*;
import com.theDrive.pagination.PagingService;
import com.theDrive.repos.BookmarkRepo;
import com.theDrive.repos.CarRepo;
import com.theDrive.repos.LikeRepo;
import com.theDrive.repos.UserRepo;
import com.theDrive.servise.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @MockBean
    private PagingService pagingService;

    @MockBean
    private CarController carController;

    @MockBean
    private CarRepo carRepo;

    @MockBean
    private LikeService likeService;

    @MockBean
    private BookmarkRepo bookmarkRepo;

    @MockBean
    private BookmarkService bookmarkService;

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private LikeRepo likeRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addLike() throws Exception {

        //given
        User user = new User("login", "pas", new Role(1L, "DRIVER"));
        Post post = new Post();
        post.setId(1L);
        Like like = new Like(post, user);

        Mockito.when(postService.getOneById(any())).thenReturn(post);

        ArgumentCaptor<Like> likeCaptor = ArgumentCaptor.forClass(Like.class);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/post/like/{postId}", "1")
                .with(csrf())
                .with(user(user)));

        Mockito.verify(likeRepo).save(likeCaptor.capture());

        //then
        Like result = likeCaptor.getValue();

        Assertions.assertEquals(like.getPost(), result.getPost());

    }

    @Test
    void removeLike() throws Exception {

        //given
        User user = new User("login", "pas", new Role(1L, "DRIVER"));
        Post post = new Post();
        post.setId(1L);
        Like like = new Like(post, user);

        Mockito.when(likeRepo.findOneByPostIdAndUserId(post.getId(), user.getId())).thenReturn(like);

        ArgumentCaptor<Like> likeCaptor = ArgumentCaptor.forClass(Like.class);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/post/like_remove/{postId}", "1")
                .with(csrf())
                .with(user(user)));

        Mockito.verify(likeRepo).delete(likeCaptor.capture());

        //then
        Like result = likeCaptor.getValue();

        Assertions.assertEquals(like.getPost(), result.getPost());
    }

    @Test
    void addBookmark() throws Exception {

        //given
        User user = new User("login", "pas", new Role(1L, "DRIVER"));
        Post post = new Post();
        post.setId(1L);

        Bookmark bookmark = new Bookmark(post, user);

        Mockito.when(postService.getOneById(post.getId())).thenReturn(post);

        ArgumentCaptor<Bookmark> bookmarkCaptor = ArgumentCaptor.forClass(Bookmark.class);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/post/bookmark/{postId}", "1")
                .with(csrf())
                .with(user(user)));

        Mockito.verify(bookmarkRepo).save(bookmarkCaptor.capture());

        //then
        Bookmark result = bookmarkCaptor.getValue();

        Assertions.assertEquals(bookmark.getPost(), result.getPost());
    }

    @Test
    void removeBookmark() throws Exception {

        //given
        User user = new User("login", "pas", new Role(1L, "DRIVER"));
        Post post = new Post();
        post.setId(1L);

        Bookmark bookmark = new Bookmark(post, user);

        Mockito.when(bookmarkRepo.findOneByPostIdAndUserId(post.getId(), user.getId())).thenReturn(bookmark);

        ArgumentCaptor<Bookmark> bookmarkCaptor = ArgumentCaptor.forClass(Bookmark.class);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/post/bookmark_remove/{postId}", "1")
                .with(csrf())
                .with(user(user)));

        Mockito.verify(bookmarkRepo).delete(bookmarkCaptor.capture());

        //then
        Bookmark result = bookmarkCaptor.getValue();

        Assertions.assertEquals(bookmark.getPost(), result.getPost());

    }
}