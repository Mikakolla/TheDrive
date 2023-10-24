package com.theDrive.servise;

import com.theDrive.entity.User;
import com.theDrive.repos.BookmarkRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepo bookmarkRepo;

    public Boolean doesPostHaveBookmarkFromUser(Long postId, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Long userId = user.getId();
        return bookmarkRepo.findOneByPostIdAndUserId(postId, userId) != null;

    }
}
