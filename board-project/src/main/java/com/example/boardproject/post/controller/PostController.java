package com.example.boardproject.post.controller;

import com.example.boardproject.exception.exceptions.NoAccessException;
import com.example.boardproject.post.model.PostResponseDTO;
import com.example.boardproject.post.model.PostRequestDTO;
import com.example.boardproject.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;  // 投稿に関するビジネスロジックを処理するサービス
    private String viewPage = "";   // 移動するビューページのパス
    private String message = "";    //ユーザーに見せるメッセージ

    // メインページに移動
    @GetMapping("")
    public String list() {
        viewPage = "postList.html";
        return viewPage;
    }

    // 投稿登録ページに移動
    @GetMapping("/write")
    public String writeView(HttpSession session) {
        // セッションからユーザーIDを取得
        Long id = (Long)session.getAttribute("id");

        // ユーザーIDがない場合、アクセスレアーを発生
        if(id == null) throw new NoAccessException("アクセス権限がありません。");

        viewPage = "postWrite.html";
        return viewPage;
    }

    // 新しい投稿を登録
    @PostMapping("/write")
    public String writeSave(
            @Valid
            @ModelAttribute PostRequestDTO postRequestDTO,
            Model model
    ) {
        // 投稿を登録
        PostResponseDTO PostResponseDTO = postService.create(postRequestDTO);

        // 登録成功する場合、該当投稿ページに移動し、登録成功メッセージを表示
        viewPage = "/board/post/" + PostResponseDTO.getId();
        message = "登録成功！";

        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // 投稿ページに移動
    @GetMapping("/post/{postId}")
    public String postView(@PathVariable Long postId, Model model) {
        // 投稿情報を取得
        PostResponseDTO PostResponseDTO = postService.readView(postId);

        // 投稿情報がある場合、該当投稿ページに移動し、投稿情報を返す
        viewPage = "postView.html";
        model.addAttribute("dto", PostResponseDTO);

        return viewPage;
    }

    // 投稿修正ページに移動
    @GetMapping("/post/{postId}/update")
    public String updateView(
            @PathVariable Long postId,
            HttpSession session,
            Model model
    ) {
        // セッションからユーザーIDを取得
        Long sessionId = (Long)session.getAttribute("id");

        // アクセス権限の有無を確認
        postService.checkAccess(postId, sessionId);

        // 投稿を取得
        PostResponseDTO PostResponseDTO = postService.postCheck(postId);

        model.addAttribute("dto", PostResponseDTO);
        viewPage = "postUpdate.html";
        return viewPage;
    }

    // 修正された投稿をアップデート
    @PostMapping("/post/{postId}/update")
    public String updateSave(
            @Valid
            @ModelAttribute PostRequestDTO postRequestDTO,
            @PathVariable Long postId,
            HttpSession session,
            Model model
    ) {
        // セッションからユーザーIDを取得
        Long sessionId = (Long)session.getAttribute("id");

        // アクセス権限の有無を確認
        postService.checkAccess(postId, sessionId);

        // 投稿をアップデート
        PostResponseDTO PostResponseDTO = postService.update(postRequestDTO, postId);

        // アップデート成功する場合、該当投稿ページに移動し、修正成功メッセージを表示
        viewPage = "/board/post/" + PostResponseDTO.getId();
        message = "修正成功！";

        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // 投稿を削除
    @PostMapping("/post/{postId}/delete")
    public String delete(
        @PathVariable Long postId,
        HttpSession session,
        Model model
    ){
        // セッションからユーザーIDを取得
        Long sessionId = (Long)session.getAttribute("id");

        // アクセス権限の有無を確認
        postService.checkAccess(postId, sessionId);

        // 投稿削除後にメインページに移動し、削除成功メッセージを表示
        postService.delete(postId);
        viewPage = "/board";
        message = "削除成功！";
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);

        return "message";
    }
}