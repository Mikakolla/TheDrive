package com.theDrive.servise;

import com.theDrive.entity.User;
import com.theDrive.repos.LikeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepo likeRepo;

    public Boolean doesPostHaveLikeFromUser(Long postId, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Long userId  = user.getId();

        return likeRepo.findOneByPostIdAndUserId(postId, userId) != null;

    }
}
