package com.example.boardproject.user.service;

import com.example.boardproject.user.db.UserEntity;
import com.example.boardproject.user.model.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    // UserEntity => UserDtoに変換して返す
    public UserResponseDTO toDto(UserEntity userEntity) {

        if (userEntity == null) return null;

        int index = userEntity.getEmail().indexOf("@");

        String email1 = userEntity.getEmail().substring(0, index);
        String email2 = userEntity.getEmail().substring(index + 1);

        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .account(userEntity.getAccount())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email1(email1)
                .email2(email2)
                .regDate(userEntity.getRegDate())
                .build();
    }
}
