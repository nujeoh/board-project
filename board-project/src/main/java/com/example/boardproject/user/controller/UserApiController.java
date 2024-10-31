package com.example.boardproject.user.controller;

import com.example.boardproject.user.model.UserCheckDTO;
import com.example.boardproject.user.model.UserResponseDTO;
import com.example.boardproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService; // ユーザーに関するビジネスロジックを処理するサービス

    // アカウントの存在を確認するエンドポイント
    @GetMapping("/account/{account}/exists")
    public ResponseEntity<Boolean> checkAccount(
             @PathVariable String account
    ){
        // アカウントの存在を返す
        return ResponseEntity.ok(userService.checkId(account));
    }

    // ユーザーIDとパスワードが一致するか確認するエンドポイント
    @PostMapping("/password/check")
    public UserResponseDTO checkPassword(
            @RequestBody UserCheckDTO user
    ){
        // 取得したユーザの情報を返す
        return userService.login(user.getAccount(), user.getPassword());
    }

}
