package com.example.boardproject.user.controller;

import com.example.boardproject.exception.exceptions.NoAccessException;
import com.example.boardproject.user.model.UserResponseDTO;
import com.example.boardproject.user.model.UserRequestDTO;
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
    private String message; // ユーザーに見せるメッセージ

    // ログインページに移動
    @GetMapping("/login")
    public String loginView(HttpSession session) {
        // セッションを無効化
        if (session != null) session.invalidate();

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
    ) {
        // アカウントとパスワードでログインを試行
        UserResponseDTO userResponseDTO = userService.login(account, password);
        if (userResponseDTO == null) {
            // ログインに失敗した場合、ログインページに移動、エラーメッセージを表示
            viewPage = "/user/login";
            message = "アカウントまたはパスワードを確認してください！";
        } else {
            // ログイン成功時、メインページに移動、歓迎メッセージを表示、セッションにユーザー情報を保存
            viewPage = "/board";
            message = userResponseDTO.getName() + "さん、ようこそ！";
            session.setAttribute("id", userResponseDTO.getId());
            session.setAttribute("userName", userResponseDTO.getName());
            session.setMaxInactiveInterval(60 * 30); // セッションの有効期限を30分に設定
        }
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);

        return "message";
    }

    // ログアウトリクエストを処理
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
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
    public String joinView(HttpSession session) {
        // セッションを無効化
        if (session != null) session.invalidate();

        viewPage = "userJoin.html";
        return viewPage;
    }

    // 会員登録リクエストを処理
    @PostMapping("/join")
    public String join(
            @Valid
            @ModelAttribute UserRequestDTO userRequestDTO,
            Model model
    ) {
        // ユーザー情報を登録
        userService.save(userRequestDTO);

        // 登録成功時、ログインページに移動、登録成功メッセージを表示
        viewPage = "/user/login";
        message = "登録成功！";

        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // マイページに移動
    @GetMapping("/mypage")
    public String myPage(Model model, HttpSession session) {
        // セッションからユーザーIDを取得
        Long id = (Long) session.getAttribute("id");

        // ユーザーIDがない場合、アクセスエラーを発生
        if (id == null) throw new NoAccessException("アクセス権限がありません。");

        // ユーザーの情報を取得
        var userDto = userService.readUser(id);

        viewPage = "myPage.html";
        model.addAttribute("dto", userDto);

        return viewPage;
    }

    // 修正されたユーザーをアップデート
    @PostMapping("/mypage")
    public String userUpdate(
            UserRequestDTO userRequestDTO,
            HttpSession session,
            Model model
    ) {
        // セッションからユーザーIDを取得
        Long sessionId = (Long) session.getAttribute("id");

        // ユーザーIDとセッションIDが一致しない場合、アクセスエラーを発生
        Long userId = userRequestDTO.getId();
        if (!sessionId.equals(userId)) throw new NoAccessException("アクセス権限がありません。");

        // ユーザーをアップデート
        userService.update(userRequestDTO, userId);

        viewPage = "/board";
        message = "修正成功！";
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // ユーザー削除リクエストを処理
    @PostMapping("/delete/{userId}")
    public String userDelete(
            @PathVariable Long userId,
            HttpSession session,
            Model model
    ) {
        // セッションからユーザーIDを取得
        Long sessionId = (Long) session.getAttribute("id");

        // ユーザーIDとセッションIDが一致しない場合、アクセスエラーを発生
        if (!sessionId.equals(userId)) throw new NoAccessException("アクセス権限がありません。");

        // ユーザーとユーザーが作成した投稿、コメント、返信コメントをすべて削除
        userService.delete(userId);

        // セッションを無効化
        session.invalidate();

        viewPage = "/board";
        message = "削除成功！";
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }
}