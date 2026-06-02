package com.social.media;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialProfile;
import com.social.media.models.SocialUser;
import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialGroupRepository;
import com.social.media.repositories.SocialProfileRepository;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataIntializer {
    private final PostRepository postRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final SocialUserRepository socialUserRepository;
    private final SocialGroupRepository socialGroupRepository;

    public DataIntializer(PostRepository postRepository, SocialProfileRepository socialProfileRepository, SocialUserRepository socialUserRepository, SocialGroupRepository socialGroupRepository) {
        this.postRepository = postRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.socialUserRepository = socialUserRepository;
        this.socialGroupRepository = socialGroupRepository;
    }
    @Bean
    public CommandLineRunner initiseData() {
        return args -> {

            // Groups
            SocialGroup group1 = new SocialGroup();
            SocialGroup group2 = new SocialGroup();
            socialGroupRepository.saveAll(List.of(group1, group2));

            // Profiles
            SocialProfile profile1 = new SocialProfile();
            SocialProfile profile2 = new SocialProfile();
            SocialProfile profile3 = new SocialProfile();

            SocialUser user1 = new SocialUser();
            user1.setSocialProfile(profile1);

            SocialUser user2 = new SocialUser();
            user2.setSocialProfile(profile2);

            SocialUser user3 = new SocialUser();
            user3.setSocialProfile(profile3);

            socialUserRepository.saveAll(List.of(user1, user2, user3));

            // Users — set all associations before saving (saved only once)
            user1.getGroups().add(group1);
            user1.getGroups().add(group2);
            user1.setSocialProfile(profile1);

            user2.getGroups().add(group1);
            user2.getGroups().add(group2);
            user2.setSocialProfile(profile2);

            user3.getGroups().add(group2);
            user3.setSocialProfile(profile3);

            socialUserRepository.saveAll(List.of(user1, user2, user3));

            // Posts
            Post post1 = new Post();
            post1.setSocialUser(user1);

            Post post2 = new Post();
            post2.setSocialUser(user2);

            Post post3 = new Post();
            post3.setSocialUser(user3);

            postRepository.saveAll(List.of(post1, post2, post3));


            //FETCH TYPES
            System.out.println("FETCHING SOCIAL USER");
            socialUserRepository.findById(1L);
        };
    }
}
