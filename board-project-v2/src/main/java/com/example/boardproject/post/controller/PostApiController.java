package com.example.boardproject.post.controller;

import com.example.boardproject.post.model.PageDto;
import com.example.boardproject.post.model.PostDto;
import com.example.boardproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;  // 投稿に関連するビジネスロジックを処理するサービス

    // 検索リクエストを処理するエンドポイント
    @GetMapping("/search")
    public PageDto searchList(
            @RequestParam(required = false, defaultValue = "") String select,
            @RequestParam String search,
            @RequestParam int page,
            @RequestParam int size
    ){
        // ページング情報を生成
        Pageable pageable = PageRequest.of(page, size);

        // 検索基準（タイトル、内容）、検索キーワード、ページング情報を渡して検索結果を返す
        return postService.searchList(select, search, pageable);
    }
}
