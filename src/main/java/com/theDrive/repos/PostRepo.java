package com.theDrive.repos;

import com.theDrive.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PostRepo extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.car.id = :carId order by p.dateCreate desc")
    List<Post> findAllPostByCarId(Long carId);

    @Query("select p from Post p where p.car.id = :carId and p.display = true order by p.dateCreate desc")
    List<Post> findAllPostByCarIdDisplayTrue(Long carId);

    @Query("select p from Post p where p.author.id = :userId order by p.dateCreate desc")
    List<Post> findAllPostByUserId(Long userId);

    Post findOneById(Long postId);

    @Query("select p from Post p where p.display = true and p.author.role.code <> 'AUTHOR' order by p.dateCreate desc")
    Page<Post> findAllOrderByDesc(Pageable pageable);

    @Query("select p from Post p where p.display = true and (p.author.id in :followsUsersId or p.car.id in :followsCarsId)")
    Page<Post> findAllPostsByFollows(Set<Long> followsUsersId, Set<Long> followsCarsId, Pageable pageable);

    @Query("select p from Post p where p.display = true and p.company is not null order by p.dateCreate desc")
    Page<Post> findAllServicesOrderByDesc(Pageable pageable);

    @Query("select p from Post p where p.display = true and p.author.role.code = 'AUTHOR' order by p.dateCreate desc")
    Page<Post> findAllNewsOrderByDesc(Pageable pageable);

    @Query("select p from Post p where p.car.brand.id in (:brandIds) " +
            "and p.car.model.id in (:modelIds)" +
            "and p.car.generation.id in (:generationIds)" +
            "and p.category.id in (:categoriesIds) " +
            "and p.display = true " +
            "and (p.author.id in :followsUsersId or p.car.id in :followsCarsId) order by dateCreate desc")
    Page<Post> findAllPostByFilter(List<Long> brandIds, List<Long> modelIds, List<Long> generationIds, List<Long> categoriesIds, Pageable pageable, Set<Long> followsUsersId, Set<Long> followsCarsId);

    @Query("select p from Post p where p.car.brand.id in (:brandIds) " +
            "and p.car.model.id in (:modelIds)" +
            "and p.car.generation.id in (:generationIds)" +
            "and p.category.id in (:categoriesIds) " +
            "and p.display = true order by dateCreate desc")
    Page<Post> findAllPostByFilter(List<Long> brandIds, List<Long> modelIds, List<Long> generationIds, List<Long> categoriesIds, Pageable pageable);

    @Query("select p from Post p where p.car.brand.id in (:brandIds) " +
            "and p.car.model.id in (:modelIds)" +
            "and p.car.generation.id in (:generationIds)" +
            "and p.category.id in (:categoriesIds) " +
            "and p.display = true " +
            "and p.company is not null order by dateCreate desc")
    Page<Post> findAllPostServicesByFilter(List<Long> brandIds, List<Long> modelIds, List<Long> generationIds, List<Long> categoriesIds, Pageable pageable);

    @Query("select p from Post p where lower(p.title) like lower(CONCAT('%',:searchText,'%')) " +
            "and p.display = true " +
            "and p.company is null " +
            "and p.author.role.code = 'AUTHOR' order by dateCreate desc")
    Page<Post> findNewsByFilter(String searchText, Pageable pageable);

    @Query("select p from Post p left join Bookmark b on p.id = b.post.id where p.id in :postId and b.user.id = :userId order by b.id desc ")
    Page<Post> findAllByIds(Set<Long> postId, Long userId, Pageable pageable);
}
