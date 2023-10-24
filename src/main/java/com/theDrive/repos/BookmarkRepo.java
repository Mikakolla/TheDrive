package com.theDrive.repos;

import com.theDrive.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepo  extends JpaRepository<Bookmark, Long> {

    @Query("select b from Bookmark b where b.post.id = :postId and b.user.id = :userId")
    Bookmark findOneByPostIdAndUserId(Long postId, Long userId);
}
