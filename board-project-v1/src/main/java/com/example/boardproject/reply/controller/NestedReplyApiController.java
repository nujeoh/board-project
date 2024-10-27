package com.example.boardproject.reply.controller;

import com.example.boardproject.reply.model.NestedReplyDto;
import com.example.boardproject.reply.model.NestedReplyRequest;
import com.example.boardproject.reply.service.NestedReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("nested-reply")
@RequiredArgsConstructor
public class NestedReplyApiController {

    private final NestedReplyService nestedReplyService;    // コメントに関するビジネスロジックを処理するサービス

    // 返信コメントの登録を処理するエンドポイント
    @PostMapping("/write")
    public NestedReplyDto write(
            @RequestBody NestedReplyRequest nestedReplyRequest
    ){
        // // 登録された返信コメントの情報を取得して返す
        return nestedReplyService.save(nestedReplyRequest);
    }

    // 返信コメントの削除を処理するエンドポイント
    @PostMapping("/delete")
    public void delete(
            @RequestParam Long id
    ){
        // NestedPostEntityのidを通じてコメントを削除
        nestedReplyService.delete(id);
    }
}
