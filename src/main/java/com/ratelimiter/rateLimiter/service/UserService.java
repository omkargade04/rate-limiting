package com.ratelimiter.rateLimiter.service;


import com.ratelimiter.rateLimiter.dto.UserDTO;
import com.ratelimiter.rateLimiter.entity.UserEntity;
import com.ratelimiter.rateLimiter.repository.UserRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    final UserRepository userRepository;
    final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer userId) {
        UserEntity userEntity = userRepository.getById(userId);
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        return modelMapper.map(userRepository.save(userEntity), UserDTO.class);
    }

    public UserDTO updateUser(Integer userId, UserDTO userDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setName(userDTO.getName());
            userRepository.save(userEntity);
            return modelMapper.map(userEntity, UserDTO.class);
        } else {
            return null; // or throw an exception if user not found
        }
    }

    public boolean deleteUser(Integer userId) {
        boolean isPresent = userRepository.existsById(userId);
        if(isPresent) {
            userRepository.deleteById(userId);
            return true;
        }else {
            return false;
        }
    }
}
