package com.example.boardproject.post.controller;

import com.example.boardproject.post.model.Pagination;
import com.example.boardproject.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;  // 投稿に関するビジネスロジックを処理するサービス

    // 検索リクエストを処理するエンドポイント
    @GetMapping("/search")
    public Pagination searchList(
            @RequestParam(required = false, defaultValue = "title") String select,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        // 渡された値がない場合、エラーが発生
        if (page == null || size == null) {
            throw new IllegalArgumentException("ページとサイズの値を指定してください。例: /board/search?page=0&size=10");
        }

        // ページ情報を生成する
        Pageable pageable = PageRequest.of(page, size);

        // 検索基準（タイトルや内容）、検索キーワード、ページ情報を渡して検索結果を返す
        return postService.searchList(select, search, pageable);
    }
}
