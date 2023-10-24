package com.theDrive.controller;

import com.theDrive.entity.*;
import com.theDrive.repos.BookmarkRepo;
import com.theDrive.repos.CarRepo;
import com.theDrive.repos.LikeRepo;
import com.theDrive.repos.UserRepo;
import com.theDrive.servise.BookmarkService;
import com.theDrive.servise.LikeService;
import com.theDrive.servise.PostService;
import com.theDrive.servise.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/post")
public class PostController {

    @Value("${upload.dir}")
    private String url;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostService postService;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private LikeService likeService;

    @Autowired
    private BookmarkRepo bookmarkRepo;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private SubscriptionService subscriptionService;


    @PostMapping("/add")
    public String addPost(@RequestParam(value = "carId", required = false) Long carId,
                        @RequestParam("title") String title,
                        @RequestParam("text") String text,
                        @RequestParam(value = "image", required = false) MultipartFile file,
                        @RequestParam(value = "price", required = false) Integer price,
                        @RequestParam(value = "mileage", required = false) Integer mileage,
                        @RequestParam(value = "display", required = false) Boolean display,
                        @RequestParam(value = "category", required = false) Long categoryId,
                        @RequestParam(value = "reviewCompanyId", required = false) Long companyId,
                        @RequestParam(value = "reviewServicesId", required = false) Long servicesId,
                          Authentication auth) throws IOException {

        User user = (User) auth.getPrincipal();

        user = userRepo.findOneById(user.getId());
        Car car = carRepo.findOneById(carId);

        UUID uuid = UUID.randomUUID();

        String pathToSave = url + "\\" + user.getId() + "\\" + uuid;

        Path pathToCreateFolder = Paths.get(pathToSave);

        File convertFile = new File(pathToSave + "\\" + file.getOriginalFilename());
        File newDirectory  = new File(convertFile, url);

        if (!newDirectory.exists()) {
            Files.createDirectories(pathToCreateFolder);
        }

        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();

        postService.savePost(title, text, user, car, uuid.toString(), file.getOriginalFilename(), price, mileage, display, companyId, servicesId, categoryId);

        if (!user.getRole().getCode().equals("AUTHOR")) {
            return "redirect:/profile/car_profile/" + carId;
        } else {
            return "redirect:/profile";
        }
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable(name = "id") Long postId, Authentication auth, Model model) {

        Post post = postService.getOneById(postId);
        User author = post.getAuthor();

        if (auth != null) {
            Boolean likeOnPostFromUser = likeService.doesPostHaveLikeFromUser(postId, auth);
            Boolean bookmarkOnPostFromUser = bookmarkService.doesPostHaveBookmarkFromUser(postId, auth);
            Boolean subscriptionUser = subscriptionService.getSubscriptionOnUser(author.getId(), auth);
            if (post.getCar() != null) {
                Boolean subscriptionCar = subscriptionService.getSubscriptionOnCar(post.getCar().getId(), auth);
                model.addAttribute("subscriptionCar", subscriptionCar);
            }

            model.addAttribute("likeOnPostFromUser", likeOnPostFromUser);
            model.addAttribute("bookmarkOnPostFromUser", bookmarkOnPostFromUser);
            model.addAttribute("subscriptionUser", subscriptionUser);
        }

        model.addAttribute("post", post);
        model.addAttribute("author", author);

        return "post";
    }

    @PostMapping("edit/{id}")
    public String editPost(@PathVariable("id") Long postId,
                           @RequestParam(value = "carId", required = false) Long carId,
                           @RequestParam("title") String title,
                           @RequestParam("text") String text,
                           @RequestParam(value = "image", required = false) MultipartFile file,
                           @RequestParam(value = "price", required = false) Integer price,
                           @RequestParam(value = "mileage", required = false) Integer mileage,
                           @RequestParam(value = "display", required = false) Boolean display,
                           @RequestParam(value = "categoryEdit", required = false) Long categoryId,
                           Authentication auth) throws IOException {

        User user = (User) auth.getPrincipal();

        user = userRepo.findOneById(user.getId());

        Car car = carRepo.findOneById(carId);

        postService.editPost(user, postId, title, text, car, file, price, mileage, display, categoryId);

        if (!user.getRole().getCode().equals("AUTHOR")) {
            return "redirect:/profile/car_profile/" + carId;
        } else {
            return "redirect:/profile";
        }
    }

    @PostMapping("/like/{postId}")
    @ResponseBody
    public void addLike(@PathVariable("postId") Long postId, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Post post = postService.getOneById(postId);

        Like like = new Like(post, user);

        likeRepo.save(like);
    }

    @PostMapping("/like_remove/{postId}")
    @ResponseBody
    public void removeLike(@PathVariable("postId") Long postId, Authentication auth) {

        User user = (User) auth.getPrincipal();

        Like like = likeRepo.findOneByPostIdAndUserId(postId, user.getId());

        likeRepo.delete(like);

    }

    @PostMapping("/bookmark/{postId}")
    @ResponseBody
    public void addBookmark(@PathVariable("postId") Long postId, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Post post = postService.getOneById(postId);

        Bookmark bookmark = new Bookmark(post, user);

        bookmarkRepo.save(bookmark);
    }

    @PostMapping("/bookmark_remove/{postId}")
    @ResponseBody
    public void removeBookmark(@PathVariable("postId") Long postId, Authentication auth) {

        User user = (User) auth.getPrincipal();

        Bookmark bookmark = bookmarkRepo.findOneByPostIdAndUserId(postId, user.getId());

        bookmarkRepo.delete(bookmark);

    }
}
