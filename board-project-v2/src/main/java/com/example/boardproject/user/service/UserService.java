package com.example.boardproject.user.service;

import com.example.boardproject.user.db.UserEntity;
import com.example.boardproject.user.db.UserRepository;
import com.example.boardproject.user.model.UserDto;
import com.example.boardproject.user.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository; // ユーザー関連のデータベース操作を処理するリポジトリ
    private final UserConverter userConverter; // EntityをDtoに変換するためのコンバータ

    // 指定されたアカウントが存在するかを確認するメソッド
    public boolean checkId(String account) {
        // アカウントの存在を確認true or falseを返す
        return userRepository.existsByAccount(account);
    }

    // 会員登録する時、新しいユーザー情報を登録するメソッド
    public UserDto save(UserRequest userRequest) {

        // ユーザー情報を基にUserEntityを生成
        var entity = UserEntity.builder()
                .account(userRequest.getAccount())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .status("REGISTERED")
                .regDate(LocalDateTime.now())
                .build();

        // 生成されたEntityをデータベースに登録
        var saveEntity = userRepository.save(entity);

        // 登録されたEntityをDtoに変換して返す
        return userConverter.toDto(saveEntity);
    }

    // ログイン処理をするメソッド
    public UserDto login(String account, String password) {
        // 指定されたアカウントとパスワードに該当するユーザーEntityを検索
        var entity = userRepository.findByAccountAndPassword(account, password);
        // 検索されたEntityをDtoに変換して返す
        return userConverter.toDto(entity);
    }
}
