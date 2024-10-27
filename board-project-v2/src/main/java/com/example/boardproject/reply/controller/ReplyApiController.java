package com.example.boardproject.reply.controller;

import com.example.boardproject.reply.model.ReplyDto;
import com.example.boardproject.reply.model.ReplyRequest;
import com.example.boardproject.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService; // コメントに関するビジネスロジックを処理するサービス

    // コメントの登録を処理するエンドポイント
    @PostMapping("/write")
    public ReplyDto write(
            @RequestBody ReplyRequest replyRequest
    ){
        // 登録されたコメントの情報を取得して返す
        return replyService.save(replyRequest);
    }

    //　コメントリストを処理するエンドポイント
    @GetMapping("/list")
    public List<ReplyDto> getReplyList(
            @RequestParam Long postId
    ){
        //　postIdを通じてコメントリストを返す
        return replyService.getReplyList(postId);
    }

    // コメントの削除を処理するエンドポイント
    @PostMapping("/delete")
    public void delete(
            @RequestParam Long id
    ){
        // PostEntityのidを通じてコメントを削除
        replyService.delete(id);
    }
}
