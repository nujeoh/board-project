package com.example.boardproject.reply.service;

import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.db.NestedReplyRepository;
import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.reply.model.NestedReplyDto;
import com.example.boardproject.reply.model.NestedReplyRequest;
import com.example.boardproject.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NestedReplyService {

    private final UserRepository userRepository; // ユーザー関連のデータベース操作を処理するリポジトリ
    private final ReplyRepository replyRepository; // コメント関連のデータベース操作を処理するリポジトリ
    private final NestedReplyRepository nestedReplyRepository; // 返信コメント関連のデータベース操作を処理するリポジトリ
    private final NestedReplyConverter nestedReplyConverter; // EntityをDtoに変換するためのコンバータ

    // 新しい返信コメントを登録するメソッド
    public NestedReplyDto save(NestedReplyRequest nestedReplyRequest) {

        // 返信コメントをつけるコメントを探す
        var replyEntity = replyRepository.findById(nestedReplyRequest.getReplyId()).get();
        // 返信コメントを書いたユーザーを探す
        var userEntity = userRepository.findById(nestedReplyRequest.getUserId()).get();

        //　NestedReplyEntityを生成
        var entity = NestedReplyEntity.builder()
                .reply(replyEntity)
                .user(userEntity)
                .userName(userEntity.getName())
                .content(nestedReplyRequest.getContent())
                .repliedAt(LocalDateTime.now())
                .build();

        // 返信コメントを登録し、Dtoに変換して返す
        return nestedReplyConverter.toDto(nestedReplyRepository.save(entity));
    }

    // 特定の返信コメントを削除するメソッド
    public void delete(Long id) {
        // 返信コメントを削除
        nestedReplyRepository.deleteById(id);
    }
}
