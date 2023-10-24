package com.theDrive.servise;

import com.theDrive.entity.*;
import com.theDrive.entity.sub.Brand;
import com.theDrive.entity.sub.Generation;
import com.theDrive.entity.sub.Model;
import com.theDrive.pagination.PagingService;
import com.theDrive.repos.*;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ModelRepo modelRepo;

    @Autowired
    private CategoryServicesRepo categoryServicesRepo;

    @Autowired
    private GenerationRepo generationRepo;

    @Autowired
    private PagingService pagingService;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private ServicesRepo servicesRepo;

    int evalPageSize = 12;

    @Value("${upload.dir}")
    private String url;

    public void savePost(String title, String text, User user, Car car, String uuid, String imageName, Integer price, Integer mileage, Boolean display, Long companyId, Long servicesId, Long categoryId) {

        Company company = null;
        Services services = null;

        CategoryServices category = categoryServicesRepo.findOneById(categoryId);

        if (display == null)
            display = false;

        if (companyId != null) {
            company = companyRepo.findOneById(companyId);
            services = servicesRepo.findOneById(servicesId);
        }

        Post post = new Post(title, text, user, new Date(), car, uuid, imageName, price, mileage, display, company, services, category);

        postRepo.save(post);

    }

    public void editPost(User user, Long postId, String title, String text, Car car, MultipartFile file, Integer price, Integer mileage, Boolean display, Long categoryId) throws IOException {

        Post post = postRepo.findOneById(postId);
        CategoryServices category = categoryServicesRepo.findOneById(categoryId);

        post.setTitle(title);
        post.setText(text);
        post.setCar(car);
        post.setCategory(category);

        if (!file.getOriginalFilename().equals("") && !post.getImageName().equals(file.getOriginalFilename())){
            //Удалить старую папку и файл
            String pathToDelete = url + "\\" + user.getId() + "\\" + post.getUuid();

            FileUtils.deleteDirectory(new File(pathToDelete));

            String imageName = file.getOriginalFilename();

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

            post.setUuid(uuid.toString());
            post.setImageName(imageName);
        }

        post.setPrice(price);
        post.setMileage(mileage);
        post.setDisplay(display);

        postRepo.save(post);

    }

    public Post getOneById(Long postId) {
        return postRepo.findOneById(postId);
    }

    public Page<Post> getAllPostsByFollows(Long userId, Pageable pageable) {

        User user = userRepo.findOneById(userId);

        Set<Long> followUser = user.getFollowUser().stream().map(subscription -> subscription.getUserFollow().getId()).collect(Collectors.toSet());
        Set<Long> followCar = user.getFollowCar().stream().map(subscription -> subscription.getCarFollow().getId()).collect(Collectors.toSet());

        Page<Post> allPostsByFollows = postRepo.findAllPostsByFollows(followUser, followCar, pageable);

        return allPostsByFollows;
    }

    public Page<Post> getAllPosts(Pageable pageable) {

        Page<Post> allPostsByFollows = postRepo.findAllOrderByDesc(pageable);

        return allPostsByFollows;
    }

    public Page<Post> getAllPostsServices(Pageable pageable) {

        Page<Post> allPostsByFollows = postRepo.findAllServicesOrderByDesc(pageable);

        return allPostsByFollows;
    }

    public Page<Post> getAllPostsNews(Pageable pageable) {

        Page<Post> allNews = postRepo.findAllNewsOrderByDesc(pageable);

        return allNews;
    }

    public HashMap<String, Object> dataForFilter() {

        HashMap<String, Object> attributes = new HashMap<>();
        List<Brand> brands = new ArrayList<>();

        brands.addAll(brandRepo.findAll());

        attributes.put("brands", brands);
        attributes.put("categories", categoryServicesRepo.findAll());

        return attributes;
    }

    //todo перенести в сервис по назначению (modelService)
    public List<Model> findAllModelByBrand(Long brandId) {
        return modelRepo.findAllByBrand(brandId);
    }

    //todo переписать на нормальный запрос
    public Page<Post> getFollowsPostsByFilter(Long brandId, Long modelId, Long generationId, Long categoryId, Pageable pageable, Long userId) {

        User user = userRepo.findOneById(userId);

        List<Long> brandIds = new ArrayList<>();
        List<Long> modelIds = new ArrayList<>();
        List<Long> generationIds = new ArrayList<>();
        List<Long> categoriesIds = new ArrayList<>();
        Set<Long> followUser = user.getFollowUser().stream().map(subscription -> subscription.getUserFollow().getId()).collect(Collectors.toSet());
        Set<Long> followCar = user.getFollowCar().stream().map(subscription -> subscription.getCarFollow().getId()).collect(Collectors.toSet());

        if (brandId == null) {
            brandIds.addAll(brandRepo.findAll().stream().map(Brand::getId).collect(Collectors.toList()));
        } else {
            brandIds.add(brandId);
        }

        if (modelId == null) {
            modelIds.addAll(modelRepo.findAll().stream().map(Model::getId).collect(Collectors.toList()));
        } else {
            modelIds.add(modelId);
        }

        if (generationId == null) {
            generationIds.addAll(generationRepo.findAll().stream().map(Generation::getId).collect(Collectors.toList()));
        } else {
            generationIds.add(generationId);
        }

        if (categoryId == null) {
            categoriesIds.addAll(categoryServicesRepo.findAll().stream().map(CategoryServices::getId).collect(Collectors.toList()));
        } else {
            categoriesIds.add(categoryId);
        }

        Page<Post> allPostByFilter = postRepo.findAllPostByFilter(brandIds, modelIds, generationIds, categoriesIds, pageable, followUser, followCar);

        return allPostByFilter;
    }

    public Page<Post> getPostsAutoByFilter(Long brandId, Long modelId, Long generationId, Long categoryId, Pageable pageable) {

        List<Long> brandIds = new ArrayList<>();
        List<Long> modelIds = new ArrayList<>();
        List<Long> generationIds = new ArrayList<>();
        List<Long> categoriesIds = new ArrayList<>();

        if (brandId == null) {
            brandIds.addAll(brandRepo.findAll().stream().map(Brand::getId).collect(Collectors.toList()));
        } else {
            brandIds.add(brandId);
        }

        if (modelId == null) {
            modelIds.addAll(modelRepo.findAll().stream().map(Model::getId).collect(Collectors.toList()));
        } else {
            modelIds.add(modelId);
        }

        if (generationId == null) {
            generationIds.addAll(generationRepo.findAll().stream().map(Generation::getId).collect(Collectors.toList()));
        } else {
            generationIds.add(generationId);
        }

        if (categoryId == null) {
            categoriesIds.addAll(categoryServicesRepo.findAll().stream().map(CategoryServices::getId).collect(Collectors.toList()));
        } else {
            categoriesIds.add(categoryId);
        }


        Page<Post> allPostByFilter = postRepo.findAllPostByFilter(brandIds, modelIds, generationIds, categoriesIds, pageable);

        return allPostByFilter;
    }

    public Page<Post> getPostsServicesByFilter(Long brandId, Long modelId, Long generationId, Long categoryId, Pageable pageable) {

        List<Long> brandIds = new ArrayList<>();
        List<Long> modelIds = new ArrayList<>();
        List<Long> generationIds = new ArrayList<>();
        List<Long> categoriesIds = new ArrayList<>();

        if (brandId == null) {
            brandIds.addAll(brandRepo.findAll().stream().map(Brand::getId).collect(Collectors.toList()));
        } else {
            brandIds.add(brandId);
        }

        if (modelId == null) {
            modelIds.addAll(modelRepo.findAll().stream().map(Model::getId).collect(Collectors.toList()));
        } else {
            modelIds.add(modelId);
        }

        if (generationId == null) {
            generationIds.addAll(generationRepo.findAll().stream().map(Generation::getId).collect(Collectors.toList()));
        } else {
            generationIds.add(generationId);
        }

        if (categoryId == null) {
            categoriesIds.addAll(categoryServicesRepo.findAll().stream().map(CategoryServices::getId).collect(Collectors.toList()));
        } else {
            categoriesIds.add(categoryId);
        }

        return postRepo.findAllPostServicesByFilter(brandIds, modelIds, generationIds, categoriesIds, pageable);
    }

    public Page<Post> getNewsByFilter(String searchText, Pageable pageable) {
        return postRepo.findNewsByFilter(searchText, pageable);
    }

    public Page<Post> getPostsWithBookmark(Long userId, int pageNumber) {

        User user = userRepo.findOneById(userId);

        Set<Long> postsIdWithBookmark = user.getBookmarks().stream().map(el -> el.getPost().getId()).collect(Collectors.toSet());

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(pageNumber, evalPageSize, sort);

        return postRepo.findAllByIds(postsIdWithBookmark, userId, pageable);
    }

}
