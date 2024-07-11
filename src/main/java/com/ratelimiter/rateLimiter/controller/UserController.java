package com.ratelimiter.rateLimiter.controller;


import com.ratelimiter.rateLimiter.dto.UserDTO;
import com.ratelimiter.rateLimiter.service.RateLimiterService;
import com.ratelimiter.rateLimiter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final RateLimiterService rateLimiterService;

    public UserController(UserService userService, RateLimiterService rateLimiterService) {
        this.userService = userService;
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId) {
        String user_id = String.valueOf(userId);
        if (!rateLimiterService.isAllowed(user_id, "getAllUsers")) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        return ResponseEntity.ok(userService.getUserById(Integer.valueOf(userId)));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        String userId = "general";
        if (!rateLimiterService.isAllowed(userId, "getAllUsers")) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
        String userId = "general";
        if(!rateLimiterService.isAllowed(userId, "createNewUser")) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userDTO));
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDTO userDTO) {
        if (!rateLimiterService.isAllowed(userId.toString(), "updateUser")) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }

    @DeleteMapping(path = "/{userId}")
    public boolean deleteUser(@PathVariable("userId") Integer userId ) {
        return userService.deleteUser(userId);
    }

}
