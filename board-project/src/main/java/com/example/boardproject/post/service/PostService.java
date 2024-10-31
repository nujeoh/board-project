package com.example.boardproject.post.service;

import com.example.boardproject.exception.exceptions.NoAccessException;
import com.example.boardproject.exception.exceptions.PostNotFoundException;
import com.example.boardproject.exception.exceptions.UserNotFoundException;
import com.example.boardproject.post.db.PostEntity;
import com.example.boardproject.post.db.PostRepository;
import com.example.boardproject.post.model.Pagination;
import com.example.boardproject.post.model.PostResponseDTO;
import com.example.boardproject.post.model.PostRequestDTO;
import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.db.NestedReplyRepository;
import com.example.boardproject.reply.db.ReplyEntity;
import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository; // ユーザーに関するデータベース操作を処理するリポジトリ
    private final PostRepository postRepository; // 投稿に関するデータベース操作を処理するリポジトリ
    private final ReplyRepository replyRepository; // コメントに関するデータベース操作を処理するリポジトリ
    private final NestedReplyRepository nestedReplyRepository; // 返信コメントに関するデータベース操作を処理するリポジトリ
    private final PostConverter postConverter; // EntityをDtoに変換するためのコンバータ

    // 投稿を検索するメソッド
    public Pagination searchList(String select, String search, Pageable pageable) {

        Page<PostEntity> postEntityList;

        if(select.equals("title")) {
            // 検索基準がタイトルの場合
            postEntityList = postRepository.findByTitleContainsOrderByIdDesc(search, pageable);
        } else if(select.equals("content")) {
            // 検索基準が内容の場合
            postEntityList = postRepository.findByContentContainsOrderByIdDesc(search, pageable);
        } else {
            // この場合は発生しない（コントローラでselectのデフォルト値が設定される）
            throw new IllegalArgumentException("予期せぬ検索基準 select:" + select + " search:" + search);
        }

        // 検索結果をDTOに変換して返す
        return postConverter.PageToDto(postEntityList);
    }

    // 新しい投稿を登録するメソッド
    public PostResponseDTO create(PostRequestDTO postRequestDTO) {

        // ユーザー情報を取得
        var userEntity = userRepository.findById(postRequestDTO.getUserId())
                .orElseThrow(
                        // ユーザーがない場合、エラー発生
                        () -> new UserNotFoundException("ユーザーが見つかりませんでした。")
                );

        // 新しいPostEntityを生成
        var entity = PostEntity.builder()
                .user(userEntity)
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .hit(0)
                .postedAt(LocalDateTime.now())
                .build();

        // 生成されたEntityをデータベースに登録
        var saveEntity = postRepository.save(entity);

        // 登録されたEntityをDtoに変換して返す
        return postConverter.toDto(saveEntity);
    }

    // 投稿の閲覧数（ビュー）を増加させ、投稿を読み込むメソッド
    public PostResponseDTO readView(Long postId) {

        // 閲覧数（ビュー）をアップデート
        int result = postRepository.updateHit(postId);
        if(result != 1) throw new RuntimeException("ビュー数の更新に失敗しました。");

        // 投稿情報を取得
        var viewEntity = postRepository.findById(postId)
                .orElseThrow(
                        // 投稿がない時、エラー発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした。")
                );

        // 取得した情報をDtoに変換して返す
        return postConverter.toDto(viewEntity);
    }

    // 投稿の存在を確認するメソッド
    public PostResponseDTO postCheck(Long postId) {

        // 投稿情報を取得
        var viewEntity = postRepository.findById(postId)
                .orElseThrow(
                        // 投稿がない場合、エラー発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした。")
                );

        // 取得した情報をDtoに変換して返す
        return postConverter.toDto(viewEntity);
    }

    // 投稿を修正するメソッド
    public PostResponseDTO update(PostRequestDTO postRequestDTO, Long postId) {

        // 投稿情報を修正してアップデート
        var entity = postRepository.findById(postId)
                .map(post -> {
                    post.setId(postId);
                    post.setTitle(postRequestDTO.getTitle());
                    post.setContent(postRequestDTO.getContent());
                    post.setPostedAt(LocalDateTime.now());
                    postRepository.save(post);
                    return post;
                }).orElseThrow(
                        // 投稿がない場合、エラー発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした。")
                );

        // 修正された情報をDtoに変換して返す
        return postConverter.toDto(entity);
    }

    // 投稿を削除するメソッド
    @Transactional //削除中にエラーが発生した場合、ロールバック
    public void delete(Long postId) {

        // 投稿を取得
        var postEntity = postRepository.findById(postId)
                .orElseThrow(
                        // 投稿がない場合、エラー発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした")
                );

        // 投稿に付いたコメントリストを取得
        List<ReplyEntity> replyEntityList = replyRepository.findByPostId(postId);

        // コメントに付いた返信コメントリストを取得
        List<NestedReplyEntity> nestedReplyEntityList = new ArrayList<>();
        for(ReplyEntity replyEntity : replyEntityList){
            List<NestedReplyEntity> deleteList = nestedReplyRepository.findByReplyId(replyEntity.getId());
            nestedReplyEntityList.addAll(deleteList);
        }

        // 投稿、コメント、返信コメントコメントを削除
        nestedReplyRepository.deleteAll(nestedReplyEntityList);
        replyRepository.deleteAll(replyEntityList);
        postRepository.delete(postEntity);
    }

    // アクセス権限の有無を確認
    public void checkAccess(Long id, Long sessionId) {
        // 投稿IDでPostEntityを取得
        var postEntity = postRepository.findById(id)
                .orElseThrow(
                        // 投稿がない場合、例エラーを発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした。")
                );

        // 作成者IDとセッションIDを比較
        if (!postEntity.getUser().getId().equals(sessionId)) {
            // アクセス権限がない場合、エラーを発生
            throw new NoAccessException("アクセス権限がありません。"); // アクセス権限がない場合、エラーを発生
        }
    }
}
