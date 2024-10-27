package com.example.boardproject.post.service;

import com.example.boardproject.post.db.PostEntity;
import com.example.boardproject.post.db.PostRepository;
import com.example.boardproject.post.model.PageDto;
import com.example.boardproject.post.model.PostDto;
import com.example.boardproject.post.model.PostRequest;
import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.db.NestedReplyRepository;
import com.example.boardproject.reply.db.ReplyEntity;
import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository; // ユーザー関連のデータベース操作を処理するリポジトリ
    private final PostRepository postRepository; // 投稿関連のデータベース操作を処理するリポジトリ
    private final ReplyRepository replyRepository; // コメント関連のデータベース操作を処理するリポジトリ
    private final NestedReplyRepository nestedReplyRepository; // 返信コメント関連のデータベース操作を処理するリポジトリ
    private final PostConverter postConverter; // EntityをDtoに変換するためのコンバータ

    // 投稿を検索するメソッド
    public PageDto searchList(String select, String search, Pageable pageable) {

        Page<PostEntity> postEntitiyList = null;

        // 検索基準がタイトルの時
        if(select.equals("title")) {
            postEntitiyList = postRepository.findByTitleContainsOrderByIdDesc(search, pageable);
        }
        // 検索基準が内容の時
        else if(select.equals("content")) {
            postEntitiyList = postRepository.findByContentContainsOrderByIdDesc(search, pageable);
        }

        // 検索結果をDTOに変換して返す
        if (postEntitiyList != null) {
            // 検索結果がある場合はPageDtoに変換して返す
            return postConverter.PageToDto(postEntitiyList);
        } else {
            // 検索結果がない場合はnullを返す
            return null;
        }
    }

    // 新しい投稿を登録するメソッド
    public PostDto create(PostRequest postRequest) {

        // ユーザー情報を取得
        var userEntity = userRepository.findById(postRequest.getUserId()).get(); // 나중에 예외처리 따로 할 것

        // 新しいPostEntityを生成
        var entity = PostEntity.builder()
                .user(userEntity)
                .userName(postRequest.getUserName())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .hit(0)
                .postedAt(LocalDateTime.now())
                .build();

        // 生成されたEntityをデータベースに登録
        var saveEntity = postRepository.save(entity);

        // 登録されたEntityをDtoに変換して返す
        return postConverter.toDto(saveEntity);
    }

    // 投稿の閲覧数（ビュー）を増加させ、投稿を読み込むメソッド
    public PostDto readView(Long postId) {
        // 閲覧数（ビュー）をアップデート
        int result = postRepository.updateHit(postId);
        if(result != 1) System.out.println("ビューのアップデートに失敗！");

        // 投稿情報を取得
        var viewEntity = postRepository.findById(postId).get();

        // 取得した情報をDtoに変換して返す
        return postConverter.toDto(viewEntity);
    }

    // 投稿の存在を確認するメソッド
    public PostDto postCheck(Long postId) {

        // 投稿情報を取得
        var viewEntity = postRepository.findById(postId).get();

        // 取得した情報をDtoに変換して返す
        return postConverter.toDto(viewEntity);
    }

    // 投稿を修正するメソッド
    public PostDto update(PostRequest postRequest, Long postId) {

        // 投稿情報を修正してアップデート
        var entity = postRepository.findById(postId)
                .map(it -> {
                    it.setId(postId);
                    it.setTitle(postRequest.getTitle());
                    it.setContent(postRequest.getContent());
                    it.setPostedAt(LocalDateTime.now());
                    postRepository.save(it);
                    return it;
                }).orElseThrow(
                        // 投稿が見つからない場合は例外を発生させる
                        () -> new RuntimeException("Post not found")
                );

        // 修正された情報をDtoに変換して返す
        return postConverter.toDto(entity);
    }

    // 投稿を削除するメソッド
    public void delete(Long postId) {

        // 投稿を削除
        postRepository.deleteById(postId);

        // 投稿に付いたコメントリストを取得
        List<ReplyEntity> replyEntityList = replyRepository.findByPostId(postId);

        // コメントに付いた返信コメントリストを取得
        List<NestedReplyEntity> nestedReplyEntityList = new ArrayList<>();
        for(ReplyEntity replyEntity : replyEntityList){
            List<NestedReplyEntity> deleteList = nestedReplyRepository.findByReplyId(replyEntity.getId());
            nestedReplyEntityList.addAll(deleteList);
        }

        // コメント、返信コメントコメントを削除
        replyRepository.deleteAll(replyEntityList);
        nestedReplyRepository.deleteAll(nestedReplyEntityList);
    }
}
