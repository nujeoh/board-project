package com.example.boardproject.reply.controller;

import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.reply.model.ReplyResponseDTO;
import com.example.boardproject.reply.model.ReplyRequestDTO;
import com.example.boardproject.reply.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService; // コメントに関するビジネスロジックを処理するサービス
    private final ReplyRepository replyRepository;

    // コメントの登録を処理するエンドポイント
    @PostMapping("/write")
    public ReplyResponseDTO write(
            @Valid
            @RequestBody ReplyRequestDTO replyRequestDTO
    ){
        // 登録されたコメントの情報を取得して返す
        return replyService.save(replyRequestDTO);
    }

    //　コメントリストを処理するエンドポイント
    @GetMapping("/list")
    public List<ReplyResponseDTO> getReplyList(
            @RequestParam Long postId
    ){
        //　postIdを通じてコメントリストを返す
        return replyService.getReplyList(postId);
    }

    // コメントの削除を処理するエンドポイント
    @PostMapping("/delete")
    public void delete(
            @RequestParam Long id,
            HttpSession session
    ){
        // セッションからユーザーIDを取得
        Long sessionId = (Long)session.getAttribute("id");

        // アクセス権限の有無を確認
        replyService.checkAccess(id, sessionId);

        // コメントと付いた返信コメントを削除
        replyService.delete(id);
    }
}
