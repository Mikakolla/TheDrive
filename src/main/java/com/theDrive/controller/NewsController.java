package com.theDrive.controller;

import com.theDrive.entity.Post;
import com.theDrive.entity.User;
import com.theDrive.entity.sub.Generation;
import com.theDrive.pagination.PagingService;
import com.theDrive.repos.GenerationRepo;
import com.theDrive.repos.ModelRepo;
import com.theDrive.servise.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final PostService postService;
    private final PagingService pagingService;
    private final ModelRepo modelRepo;
    private final GenerationRepo generationRepo;

    int evalPageSize = 9;

    @GetMapping("/follows")
    public String getFollowsNews(Model model, Authentication auth) {

        User user = (User) auth.getPrincipal();

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(0, evalPageSize, sort);

        Page<Post> posts = postService.getAllPostsByFollows(user.getId(), pageable);

        model.addAttribute("posts", posts);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(0, posts.getTotalPages(), posts.getTotalElements(), "dateCreate", "desc"));

        return "news_feed";
    }

    @GetMapping("/filter")
    public String getNewsFromFilter(@RequestParam(name = "brand", required = false) Long brandId,
                                     @RequestParam(name = "model", required = false) Long modelId,
                                     @RequestParam(name = "generation", required = false) Long generationId,
                                     @RequestParam(name = "category", required = false) Long categoryId,
                                     @RequestParam(name = "pageNo") Optional<Integer> pageNo,
                                     Authentication auth,
                                     Model model) {

        User user = (User) auth.getPrincipal();

        int evalPage = pagingService.validatePageNo(pageNo);

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);

        Page<Post> postsByFilter = postService.getFollowsPostsByFilter(brandId, modelId, generationId, categoryId, pageable, user.getId());

        model.addAttribute("posts", postsByFilter);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(evalPage, postsByFilter.getTotalPages(), postsByFilter.getTotalElements(), "dateCreate", "desc"));

        return "news_feed::posts";

    }

    @GetMapping("/models")
    @ResponseBody
    public List<com.theDrive.entity.sub.Model> getModelsByBrand(@RequestParam(name = "brandId") Long brandId) {
        return modelRepo.findAllByBrand(brandId);
    }

    @GetMapping("/generation")
    @ResponseBody
    public List<Generation> getGenerationsByModel(@RequestParam(name = "modelId") Long modelId) {
        return generationRepo.findAllByModelId(modelId);
    }

    @GetMapping("/auto")
    public String getAutoNews(Model model) {

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(0, evalPageSize, sort);

        Page<Post> posts = postService.getAllPosts(pageable);

        model.addAttribute("posts", posts);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(0, posts.getTotalPages(), posts.getTotalElements(), "dateCreate", "desc"));

        return "news_feed_auto";
    }

    @GetMapping("/filter_auto")
    public String getNewsAutoFromFilter(@RequestParam(name = "brand", required = false) Long brandId,
                                    @RequestParam(name = "model", required = false) Long modelId,
                                    @RequestParam(name = "generation", required = false) Long generationId,
                                    @RequestParam(name = "category", required = false) Long categoryId,
                                    @RequestParam(name = "pageNo") Optional<Integer> pageNo,
                                    Model model) {

        int evalPage = pagingService.validatePageNo(pageNo);

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);

        Page<Post> postsByFilter = postService.getPostsAutoByFilter(brandId, modelId, generationId, categoryId, pageable);

        model.addAttribute("posts", postsByFilter);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(evalPage, postsByFilter.getTotalPages(), postsByFilter.getTotalElements(), "dateCreate", "desc"));

        return "news_feed_auto::posts";
    }

    @GetMapping("/brand_auto")
    public String getNewsForBrand(@RequestParam(name = "brand", required = false) Long brandId,
                                        Model model) {

        int evalPage = 0;

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);

        Page<Post> postsByFilter = postService.getPostsAutoByFilter(brandId, null, null, null, pageable);

        model.addAttribute("posts", postsByFilter);
        model.addAttribute("chooseBrandId", brandId);

        if (brandId != null) {
            model.addAttribute("models", postService.findAllModelByBrand(brandId));
        }

        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(evalPage, postsByFilter.getTotalPages(), postsByFilter.getTotalElements(), "dateCreate", "desc"));

        return "news_feed_auto";

    }

    @GetMapping("/services")
    public String getServicesNews(Model model) {

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(0, evalPageSize, sort);

        Page<Post> posts = postService.getAllPostsServices(pageable);

        model.addAttribute("posts", posts);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(0, posts.getTotalPages(), posts.getTotalElements(), "dateCreate", "desc"));

        return "news_feed_services";
    }

    @GetMapping("/filter_service")
    public String getNewsServicesFromFilter(@RequestParam(name = "brand", required = false) Long brandId,
                                        @RequestParam(name = "model", required = false) Long modelId,
                                        @RequestParam(name = "generation", required = false) Long generationId,
                                        @RequestParam(name = "category", required = false) Long categoryId,
                                        @RequestParam(name = "pageNo") Optional<Integer> pageNo,
                                        Model model) {

        int evalPage = pagingService.validatePageNo(pageNo);

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);

        Page<Post> postsByFilter = postService.getPostsServicesByFilter(brandId, modelId, generationId, categoryId, pageable);

        model.addAttribute("posts", postsByFilter);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(evalPage, postsByFilter.getTotalPages(), postsByFilter.getTotalElements(), "dateCreate", "desc"));

        return "news_feed_services::posts";
    }


    @GetMapping("/news")
    public String getNews(Model model) {

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(0, evalPageSize, sort);

        Page<Post> posts = postService.getAllPostsNews(pageable);

        model.addAttribute("posts", posts);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(0, posts.getTotalPages(), posts.getTotalElements(), "dateCreate", "desc"));

        return "news";
    }

    @GetMapping("/filter_news")
    public String getNewsFromFilter(@RequestParam(name = "search_text") String searchText,
                                    @RequestParam(name = "pageNo") Optional<Integer> pageNo,
                                    Model model) {

        int evalPage = pagingService.validatePageNo(pageNo);

        Sort sort = Sort.by("dateCreate").descending();
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, sort);

        Page<Post> postsByFilter = postService.getNewsByFilter(searchText, pageable);

        model.addAttribute("posts", postsByFilter);
        model.addAttribute("searchText", searchText);
        model.addAllAttributes(postService.dataForFilter());
        model.addAllAttributes(pagingService.preparePagingParams(evalPage, postsByFilter.getTotalPages(), postsByFilter.getTotalElements(), "dateCreate", "desc"));

        return "news";
    }
}
