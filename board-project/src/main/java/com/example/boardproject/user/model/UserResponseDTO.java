package com.example.boardproject.user.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponseDTO {
    // Entityの情報をDtoに変換（クライアントに提供するため）

    private Long id;

    private String account;

    private String name;

    private String phone;

    private String email1;

    private String email2;

    private LocalDateTime regDate;
}
