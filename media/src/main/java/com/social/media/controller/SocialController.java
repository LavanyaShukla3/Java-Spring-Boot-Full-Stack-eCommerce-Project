package com.social.media.controller;

import com.social.media.models.SocialUser;
import com.social.media.services.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialController {
    @Autowired
    public SocialService socialService;

    @GetMapping("/social/users")
    public ResponseEntity<List<SocialUser>> getUsers() {
        return new ResponseEntity<>(socialService.getAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/social/users")
    public ResponseEntity<SocialUser> saveUsers(@RequestBody SocialUser socialUser) {
        return new ResponseEntity<>(socialService.saveUser(socialUser), HttpStatus.CREATED);
    }
    @DeleteMapping("/social/users/{userId}")
    public ResponseEntity<SocialUser> deleteUsers(@PathVariable Long userId) {
        return new ResponseEntity<>(socialService.deleteUser(userId), HttpStatus.OK);
    }
}
