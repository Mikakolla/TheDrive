package com.theDrive.controller;

import com.theDrive.entity.*;
import com.theDrive.entity.sub.*;
import com.theDrive.pagination.PagingService;
import com.theDrive.repos.*;
import com.theDrive.servise.PostService;
import com.theDrive.servise.ProfileService;
import com.theDrive.servise.SubscriptionService;
import com.theDrive.servise.UseServicesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;
    private final CarRepo carRepo;
    private final SubscriptionService subscriptionService;
    private final PostRepo postRepo;
    private final CategoryServicesRepo categoryServicesRepo;
    private final ProfileService profileService;
    private final PostService postService;
    private final BrandRepo brandRepo;
    private final PagingService pagingService;
    private final UseServicesService useServicesService;
    private final ModelRepo modelRepo;
    private final GenerationRepo generationRepo;
    private final DriveRepo driveRepo;
    private final BodyRepo bodyRepo;
    private final EngineRepo engineRepo;

    @GetMapping
    public String getProfile(Model model, Authentication auth) {

        User user = (User) auth.getPrincipal();

        user = userRepo.findOneById(user.getId());

        Company company = companyRepo.findOneByUserId(user.getId());
        Page<Post> postsWithBookmark = postService.getPostsWithBookmark(user.getId(), 0);
//        List<Car> cars = user.getCars();


        model.addAttribute("user", user);
        model.addAttribute("postsBookmarks", postsWithBookmark);
        model.addAllAttributes(pagingService.preparePagingParams(0, postsWithBookmark.getTotalPages(), postsWithBookmark.getTotalElements(), "id", "desc"));
        model.addAllAttributes(profileService.getAllProfile(user.getId()));
//        model.addAttribute("cars", profileService.getProfile(user.getId()));
//        model.addAttribute("company", company);

        return "profile";
    }

    @GetMapping("/{id}")
    public String getProfileUser(@PathVariable("id") Long userId, Authentication auth, Model model) {

        User user;

        if (auth != null) {
            user = (User) auth.getPrincipal();
            if (userId.equals(user.getId()))
                return getProfile(model, auth);
        }

        model.addAllAttributes(profileService.getProfile(userId, auth));

        return "user";
    }

    @GetMapping("/car_profile/{id}")
    public String getProfileCar(@PathVariable("id") Long carId,
                                Authentication auth, Model model) {

        Car car = carRepo.findOneById(carId);
        Integer followers = subscriptionService.getCountFollowersCar(carId);
        List<Post> postsByCar;

        if (auth != null && ((User) auth.getPrincipal()).getId() == car.getUser().getId()) {
            postsByCar = postRepo.findAllPostByCarId(carId);
        } else {
            postsByCar = postRepo.findAllPostByCarIdDisplayTrue(carId);
            if (auth != null) {
                Boolean subscriptionCar = subscriptionService.getSubscriptionOnCar(carId, auth);
                model.addAttribute("subscriptionCar", subscriptionCar);
            }
        }


        List<Brand> brands = brandRepo.findAll();
        List<UseServices> useServices = useServicesService.getAllByCar(carId);
        List<com.theDrive.entity.sub.Model> models = modelRepo.findAllByBrand(car.getBrand().getId());
        List<Generation> generations = generationRepo.findAllByModelId(car.getModel().getId());
        List<Body> bodys = bodyRepo.findAll();
        List<Drive> drives = driveRepo.findAll();
        List<Engine> engines = engineRepo.findAll();
        List<CategoryServices> categoryServices = categoryServicesRepo.findAll();

        model.addAttribute("car", car);
        model.addAttribute("brands", brands);
        model.addAttribute("models", models);
        model.addAttribute("generations", generations);
        model.addAttribute("bodys", bodys);
        model.addAttribute("drives", drives);
        model.addAttribute("engines", engines);
        model.addAttribute("posts", postsByCar);
        model.addAttribute("categoryServices", categoryServices);
        model.addAttribute("useServices", useServices);
        model.addAttribute("followers", followers);

        return "car_profile";
    }

    @GetMapping("/pagination")
    public String getPageWithPosts(@RequestParam(name = "pageNo") Optional<Integer> pageNo,
                                   Authentication auth,
                                   Model model) {

        int evalPage = pagingService.validatePageNo(pageNo);

        User user = (User) auth.getPrincipal();

        Page<Post> postsWithBookmark = postService.getPostsWithBookmark(user.getId(), evalPage);

        model.addAttribute("postsBookmarks", postsWithBookmark);
        model.addAllAttributes(pagingService.preparePagingParams(evalPage, postsWithBookmark.getTotalPages(), postsWithBookmark.getTotalElements(), "id", "desc"));

        return "profile::postBookmark";
    }
}
