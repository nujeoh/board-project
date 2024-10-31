package com.example.boardproject.reply.service;

import com.example.boardproject.exception.exceptions.NoAccessException;
import com.example.boardproject.exception.exceptions.PostNotFoundException;
import com.example.boardproject.exception.exceptions.ReplyNotFoundException;
import com.example.boardproject.exception.exceptions.UserNotFoundException;
import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.db.NestedReplyRepository;
import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.reply.model.NestedReplyResponseDTO;
import com.example.boardproject.reply.model.NestedReplyRequestDTO;
import com.example.boardproject.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NestedReplyService {

    private final UserRepository userRepository; // ユーザーに関するデータベース操作を処理するリポジトリ
    private final ReplyRepository replyRepository; // コメントに関するデータベース操作を処理するリポジトリ
    private final NestedReplyRepository nestedReplyRepository; // 返信コメントに関するデータベース操作を処理するリポジトリ
    private final NestedReplyConverter nestedReplyConverter; // EntityをDtoに変換するためのコンバータ

    // 新しい返信コメントを登録するメソッド
    public NestedReplyResponseDTO save(NestedReplyRequestDTO nestedReplyRequestDTO) {

        // 返信コメントをつけるコメントを取得
        var replyEntity = replyRepository.findById(nestedReplyRequestDTO.getReplyId())
                .orElseThrow(
                        // 投稿がない場合、エラー発生
                        () -> new PostNotFoundException("投稿が見つかりませんでした。")
                );
        // 返信コメントを書いたユーザーを取得
        var userEntity = userRepository.findById(nestedReplyRequestDTO.getUserId())
                .orElseThrow(
                        // ユーザーがない場合、エラー発生
                        () -> new UserNotFoundException("ユーザーが見つかりませんでいた。")
                );

        //　NestedReplyEntityを生成
        var entity = NestedReplyEntity.builder()
                .reply(replyEntity)
                .user(userEntity)
                .userName(userEntity.getName())
                .content(nestedReplyRequestDTO.getContent())
                .repliedAt(LocalDateTime.now())
                .build();

        // 返信コメントを登録し、Dtoに変換して返す
        return nestedReplyConverter.toDto(nestedReplyRepository.save(entity));
    }

    // 特定の返信コメントを削除するメソッド
    public void delete(Long id) {
        // 返信コメントを削除
        var nestedReplyEntity = nestedReplyRepository.findById(id).orElseThrow(
                // 返信コメントがない場合、エラー発生
                () -> new ReplyNotFoundException("返信コメントが見つかりませんでした。")
        );
        nestedReplyRepository.delete(nestedReplyEntity);
    }

    // アクセス権限の有無を確認
    public void checkAccess(Long id, Long sessionId) {
        // 返信コメントIDでNestedReplyEntityを取得
        var nestedReplyEntity = nestedReplyRepository.findById(id)
                .orElseThrow(
                        // 返信コメントがない場合、例エラーを発生
                        () -> new ReplyNotFoundException("返信コメントが見つかりませんでした。")
                );

        // 作成者IDとセッションIDを比較
        if (!nestedReplyEntity.getUser().getId().equals(sessionId)) {
            // アクセス権限がない場合、エラーを発生
            throw new NoAccessException("アクセス権限がありません。");
        }
    }
}
