package com.example.boardproject.reply.controller;

import com.example.boardproject.reply.model.NestedReplyResponseDTO;
import com.example.boardproject.reply.model.NestedReplyRequestDTO;
import com.example.boardproject.reply.service.NestedReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("nested-reply")
@RequiredArgsConstructor
public class NestedReplyApiController {

    private final NestedReplyService nestedReplyService;    // コメントに関するビジネスロジックを処理するサービス

    // 返信コメントの登録を処理するエンドポイント
    @PostMapping("/write")
    public NestedReplyResponseDTO write(
            @RequestBody NestedReplyRequestDTO nestedReplyRequestDTO
    ){
        // 登録された返信コメントの情報を取得して返す
        return nestedReplyService.save(nestedReplyRequestDTO);
    }

    // 返信コメントの削除を処理するエンドポイント
    @PostMapping("/delete")
    public void delete(
            @RequestParam Long id,
            HttpSession session
    ){
        // セッションからユーザーIDを取得
        Long sessionId = (Long)session.getAttribute("id");

        // アクセス権限の有無を確認
        nestedReplyService.checkAccess(id, sessionId);

        // 返信コメントを削除
        nestedReplyService.delete(id);
    }
}
