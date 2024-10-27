package com.example.boardproject.user.service;

import com.example.boardproject.user.db.UserEntity;
import com.example.boardproject.user.model.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    // UserEntity => UserDtoに変換
    public UserDto toDto(UserEntity userEntity) {

        if (userEntity == null) return null;

        return UserDto.builder()
                .id(userEntity.getId())
                .account(userEntity.getAccount())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .regDate(userEntity.getRegDate())
                .build();
    }
}
