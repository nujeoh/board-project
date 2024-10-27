package com.example.boardproject.post.controller;

import com.example.boardproject.post.model.PostDto;
import com.example.boardproject.post.model.PostRequest;
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
    public String list(Model model) {
        viewPage = "postList.html";
        return viewPage;
    }

    // 投稿登録ページに移動
    @GetMapping("/write")
    public String writeView(HttpSession session, Model model) {

        // sessionからユーザーIDを取得
        Long id = (Long)session.getAttribute("id");

        // ログインしているか確認
        if(id == null) {
            // ユーザーIDがない場合はメインページに移動し、アクセス制限メッセージを表示
            viewPage = "/board";
            message = "アクセス権限がありません。";
            model.addAttribute("viewPage", viewPage);
            model.addAttribute("message", message);
            return "message";
        } else {
            // ユーザーIDがある場合は投稿作成ページに移動
            viewPage = "postWrite.html";
            return viewPage;
        }
    }

    // 新しい投稿を登録
    @PostMapping("/write")
    public String writeSave(
            @Valid
            @ModelAttribute PostRequest postRequest,
            Model model
    ) {
        // 投稿を登録
        PostDto PostDto = postService.create(postRequest);

        if (PostDto != null) {
            // 登録成功時は該当投稿ページに移動し、登録成功メッセージを表示
            viewPage = "/board/post/" + PostDto.getId();
            message = "登録成功！";
        } else {
            // 登録失敗時はメインページに移動し、登録失敗メッセージを表示
            viewPage = "/board";
            message = "登録失敗！";
        }
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // 投稿ページに移動
    @GetMapping("/post/{postId}")
    public String postView(
            @PathVariable Long postId,
            Model model
    ) {
        // 投稿情報を取得
        PostDto PostDto = postService.readView(postId);

        if (PostDto != null) {
            // 投稿情報がある場合は該当投稿ページに移動し、投稿情報を返す
            viewPage = "postView.html";
            model.addAttribute("dto", PostDto);
        } else {
            // 投稿情報がない場合はメインページに移動し、投稿が存在しないというメッセージを表示
            viewPage = "/board";
            message = "存在しない投稿です。";
            model.addAttribute("viewPage", viewPage);
            model.addAttribute("message", message);
            return "message";
        }
        return viewPage;
    }

    // 投稿修正ページに移動
    @GetMapping("/post/{postId}/update")
    public String updateView(
            @PathVariable Long postId,
            HttpSession session,
            Model model
    ) {
        // sessionからユーザーIDを取得
        Long id = (Long)session.getAttribute("id");

        // 修正しようとする投稿情報を取得
        PostDto PostDto = postService.postCheck(postId);

        // 投稿が存在しない場合はメインページに移動し、投稿が存在しないというメッセージを表示
        if (PostDto == null) {
            viewPage = "/board";
            message = "存在しない投稿です";
            model.addAttribute("viewPage", viewPage);
            model.addAttribute("message", message);
            return "message";
        }

        // ユーザーIDがなかったり、作成者IDとユーザーIDが一致しない場合はメインページに移動し、アクセス制限メッセージを表示
        if (!PostDto.getUserId().equals(id) || id == null) {
            viewPage = "/board";
            message = "アクセス権限がありません。";
            model.addAttribute("viewPage", viewPage);
            model.addAttribute("message", message);
            return "message";
        }

        // すべての条件を満たす場合は修正ページに移動
        model.addAttribute("dto", PostDto);
        viewPage = "postUpdate.html";
        return viewPage;
    }

    // 修正された投稿をアップデート
    @PostMapping("/post/{postId}/update")
    public String updateSave(
            @Valid
            @ModelAttribute PostRequest postRequest,
            @PathVariable Long postId,
            Model model
    ) {
        // 投稿をアップデート
        PostDto PostDto = postService.update(postRequest, postId);
        if (PostDto != null) {
            // アップデート成功時は該当投稿ページに移動し、修正成功メッセージを表示
            viewPage = "/board/post/" + PostDto.getId();
            message = "修正成功！";
        } else {
            // アップデート失敗時はメインページに移動し、修正失敗メッセージを表示
            viewPage = "/board";
            message = "修正失敗！";
        }
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);
        return "message";
    }

    // 投稿削除確認ページに移動
    @GetMapping("/post/{postId}/delete")
    public String deleteConfirm(
        @PathVariable Long postId,
        HttpSession session,
        Model model
    ){
        // sessionからユーザーIDを取得
        Long id = (Long)session.getAttribute("id");

        // 削除しようとする投稿情報を取得
        PostDto PostDto = postService.postCheck(postId);

        // 投稿が存在しない場合はメインページに移動し、投稿が存在しないというメッセージを表示
        if (PostDto == null) {
            viewPage = "/board";
            message = "存在しない投稿です";
            model.addAttribute("viewPage", viewPage);
            model.addAttribute("message", message);
            return "message";
        }

        // ユーザーIDがなかったり、作成者IDとユーザーIDが一致しない場合はメインページに移動し、アクセス制限メッセージを表示
        if (!PostDto.getUserId().equals(id) || id == null) {
            viewPage = "/board";
            message = "アクセス権限がありません。";
            model.addAttribute("viewPage", viewPage);
            model.addAttribute("message", message);
            return "message";
        }

        // すべての条件を満たす場合は修正ページに移動
        model.addAttribute("dto", PostDto);
        return "postDelete.html";
    }

    // 投稿を削除
    @PostMapping("/post/{postId}/delete")
    public String delete(
        @PathVariable Long postId,
        Model model
    ){
        // 投稿削除後にメインページに移動し、削除成功メッセージを表示
        postService.delete(postId);
        viewPage = "/board";
        message = "削除成功！";
        model.addAttribute("viewPage", viewPage);
        model.addAttribute("message", message);

        return "message";
    }
}