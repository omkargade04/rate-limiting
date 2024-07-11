package com.ratelimiter.rateLimiter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String name;

    public Integer getUserId() {
        return userId;
    }

    public Object getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
