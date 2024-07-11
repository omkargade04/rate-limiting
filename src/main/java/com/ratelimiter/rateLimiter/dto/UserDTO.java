package com.ratelimiter.rateLimiter.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer userId;
    private String name;

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public UserDTO(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

}
