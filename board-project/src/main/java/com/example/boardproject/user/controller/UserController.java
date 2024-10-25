package com.example.boardproject.user.controller;

import com.example.boardproject.user.model.UserDto;
import com.example.boardproject.user.model.UserRequest;
import com.example.boardproject.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; // ユーザーに関するビジネスロジックを処理するサービス
    private String viewPage; // 移動するビューページのパス
    private String message; //ユーザーに見せるメッセージ

    // ログインページに移動
    @GetMapping("/login")
    public String loginView(){
        viewPage = "userLogin.html";
        return viewPage;
    }

    // ログイントリクエストを処理
    @PostMapping("/login")
    public String login(
            @RequestParam(defaultValue = "") String account,
            @RequestParam(defaultValue = "") String password,
            Model model,
            HttpSession session
    ){
        // アカウントとパスワードでログイン試行
        UserDto userDto = userService.login(account, password);
        if(userDto == null){
            // ログインに失敗時はログインページに移動、エラーメッセージを表示
            viewPage = "/user/login";
            message = "アカウントまたはパスワードを確認してください！";
        } else {
            // ログイン成功時はメインページに移動、歓迎メッセージを表示、sessionにユーザー情報を保存
            viewPage = "/board";
            message = userDto.getName() + "さん、ようこそ！";
            session.setAttribute("id", userDto.getId());
            session.setAttribute("userName", userDto.getName());
            session.setMaxInactiveInterval(60 * 30);
        }
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);

        return "message";
    }

    // ログアウトリクエストを処理
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model){
        // セッションからユーザー名を取得
        String userName = (String) session.getAttribute("userName");

        // セッションを無効化
        session.invalidate();

        // メインページに移動、ログアウトメッセージを表示
        viewPage = "/board";
        message = userName + "さん、ログアウトしました！";
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // 会員登録ページに移動
    @GetMapping("/join")
    public String joinView(){
        viewPage = "userJoin.html";
        return viewPage;
    }

    // 登録リクエストを処理
    @PostMapping("/join")
    public String join(
        @Valid
        @ModelAttribute UserRequest userRequest,
        Model model
    ){
        // ユーザー情報を登録
        UserDto userDto = userService.save(userRequest);
        if(userDto.getId() != null){
            // 登録成功時はログインページに移動、登録成功メッセージを表示
            viewPage = "/user/login";
            message = "登録成功！";
        } else {
            // 登録失敗時はメインページに移動、登録失敗メッセージを表示
            viewPage = "/board";
            message = "登録失敗！";
        }
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }
}
