package com.social.media.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "socialProfile")
    @JsonIgnore
    private SocialUser user;

    public void setSocialUser(SocialUser socialUser) {
        this.user = socialUser;
        if(user.getSocialProfile() != this){
            user.setSocialProfile(this);
        }
    }

}
