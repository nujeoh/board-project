package com.example.boardproject.reply.service;

import com.example.boardproject.exception.exceptions.NoAccessException;
import com.example.boardproject.exception.exceptions.PostNotFoundException;
import com.example.boardproject.exception.exceptions.ReplyNotFoundException;
import com.example.boardproject.exception.exceptions.UserNotFoundException;
import com.example.boardproject.post.db.PostRepository;
import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.db.NestedReplyRepository;
import com.example.boardproject.reply.db.ReplyEntity;
import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.reply.model.ReplyResponseDTO;
import com.example.boardproject.reply.model.ReplyRequestDTO;
import com.example.boardproject.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final UserRepository userRepository; // ユーザーに関するデータベース操作を処理するリポジトリ
    private final PostRepository postRepository; // 投稿に関するデータベース操作を処理するリポジトリ
    private final ReplyRepository replyRepository; // コメントに関するデータベース操作を処理するリポジトリ
    private final NestedReplyRepository nestedReplyRepository; // 返信コメントに関するデータベース操作を処理するリポジトリ
    private final ReplyConverter replyConverter; // EntityをDtoに変換するためのコンバータ

    // 新しいコメントを登録するメソッド
    public ReplyResponseDTO save(ReplyRequestDTO replyRequestDTO) {

        // コメントをつける投稿を取得
        var postEntity = postRepository.findById(replyRequestDTO.getPostId())
                .orElseThrow(
                        // 投稿がない場合、エラー発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした。")
                );
        // コメントを書いたユーザーを取得
        var userEntity = userRepository.findById(replyRequestDTO.getUserId())
                .orElseThrow(
                        // ユーザーがない場合、エラー発生
                        () -> new UserNotFoundException("ユーザーが見つかりませんでいた。")
                );

        //　ReplyEntityを生成
        var entity = ReplyEntity.builder()
                .post(postEntity)
                .user(userEntity)
                .userName(userEntity.getName())
                .content(replyRequestDTO.getContent())
                .repliedAt(LocalDateTime.now())
                .build();

        // コメントを登録し、Dtoに変換して返す
        return replyConverter.toDto(replyRepository.save(entity));
    }

    // 特定の投稿のコメントリストを取得するメソッド
    public List<ReplyResponseDTO> getReplyList(Long postId) {

        // postIdでコメントリストを取得
        List<ReplyEntity> replyEntityList = replyRepository.findByPostId(postId);

        // 取得したコメントリストをDtoリストに変換して返す
        return replyConverter.toDtoList(replyEntityList);
    }



    // 特定のコメントを削除するメソッド
    @Transactional //削除中にエラーが発生した場合、ロールバック
    public void delete(Long replyId) {

        // コメントを取得
        var replyEntity = replyRepository.findById(replyId)
                .orElseThrow(
                        // コメントがない場合、エラー発生
                        () -> new ReplyNotFoundException("コメントが見つかりませんでした")
                );

        // そのコメントに付いている全ての返信コメントを取得
        List<NestedReplyEntity> NestedReplyList = nestedReplyRepository.findByReplyId(replyId);

        // コメント、返信コメントコメントを削除
        nestedReplyRepository.deleteAll(NestedReplyList);
        replyRepository.delete(replyEntity);
    }

    // アクセス権限の有無を確認
    public void checkAccess(Long id, Long sessionId) {
        // コメントIDでReplyEntityを取得
        var replyEntity = replyRepository.findById(id)
                // コメントがない場合、例エラーを発生
                .orElseThrow(() -> new ReplyNotFoundException("コメントが見つかりませんでした。"));

        // 作成者IDとセッションIDを比較
        if (!replyEntity.getUser().getId().equals(sessionId)) {
            // アクセス権限がない場合、エラーを発生
            throw new NoAccessException("アクセス権限がありません。");
        }
    }
}
