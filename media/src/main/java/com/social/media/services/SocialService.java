package com.social.media.services;

import com.social.media.models.SocialUser;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    private SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {
        return socialUserRepository.findAll();
    }
    public SocialUser saveUser(SocialUser socialUser) {
        socialUser.setId(null);
        if (socialUser.getSocialProfile() != null) {
            socialUser.getSocialProfile().setId(null);
        }
        return socialUserRepository.save(socialUser);
    }

    public SocialUser deleteUser(Long Id) {
        SocialUser socialUser = socialUserRepository.findById(Id).orElseThrow(() -> new RuntimeException("User not found with id: " + Id));
        socialUser.getGroups().clear();
        for (var post : socialUser.getPosts()) {
            post.setSocialUser(null);
        }
        socialUser.getPosts().clear();
        if (socialUser.getSocialProfile() != null) {
            socialUser.getSocialProfile().setUser(null);
        }
        socialUser.setSocialProfile(null);
        socialUserRepository.save(socialUser);
        socialUserRepository.delete(socialUser);
        return socialUser;
    }
}
