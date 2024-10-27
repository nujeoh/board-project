package com.example.boardproject.user.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user") // データベースのuserというテーブルとマッチングしてマッピング
public class UserEntity {

    @Id // primary keyに設定
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary keyの生成をデータベースに委任 - MySQL auto_increment
    private Long id;

    private String account;

    private String password;

    private String name;

    private String phone;

    private String email;

    private String status;

    private LocalDateTime regDate;
}
