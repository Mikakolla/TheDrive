package com.theDrive.repos;

import com.theDrive.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepo extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.post.id = :postId and l.user.id = :userId")
    Like findOneByPostIdAndUserId(Long postId, Long userId);
}
