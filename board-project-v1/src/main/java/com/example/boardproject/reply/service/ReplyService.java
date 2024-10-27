package com.example.boardproject.reply.service;

import com.example.boardproject.post.db.PostRepository;
import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.db.NestedReplyRepository;
import com.example.boardproject.reply.db.ReplyEntity;
import com.example.boardproject.reply.db.ReplyRepository;
import com.example.boardproject.reply.model.ReplyDto;
import com.example.boardproject.reply.model.ReplyRequest;
import com.example.boardproject.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final UserRepository userRepository; // ユーザー関連のデータベース操作を処理するリポジトリ
    private final PostRepository postRepository; // 投稿関連のデータベース操作を処理するリポジトリ
    private final ReplyRepository replyRepository; // コメント関連のデータベース操作を処理するリポジトリ
    private final NestedReplyRepository nestedReplyRepository; // 返信コメント関連のデータベース操作を処理するリポジトリ
    private final ReplyConverter replyConverter; // EntityをDtoに変換するためのコンバータ

    // 新しいコメントを登録するメソッド
    public ReplyDto save(ReplyRequest replyRequest) {

        // コメントをつける投稿を探す
        var postEntity = postRepository.findById(replyRequest.getPostId()).get();
        // コメントを書いたユーザーを探す
        var userEntity = userRepository.findById(replyRequest.getUserId()).get();

        //　ReplyEntityを生成
        var entity = ReplyEntity.builder()
                .post(postEntity)
                .user(userEntity)
                .userName(userEntity.getName())
                .content(replyRequest.getContent())
                .repliedAt(LocalDateTime.now())
                .build();

        // コメントを登録し、Dtoに変換して返す
        return replyConverter.toDto(replyRepository.save(entity));
    }

    // 特定の投稿のコメントリストを取得するメソッド
    public List<ReplyDto> getReplyList(Long postId) {

        // postIdでコメントリストを取得
        List<ReplyEntity> replyEntityList = replyRepository.findByPostId(postId);

        // 取得したコメントリストをDtoリストに変換して返す
        return replyConverter.toDtoList(replyEntityList);
    }


    // 特定のコメントを削除するメソッド
    public void delete(Long replyId) {

        // コメントを削除
        replyRepository.deleteById(replyId);

        // そのコメントに付いている全ての返信コメントを取得して削除
        List<NestedReplyEntity> NestedReplyList = nestedReplyRepository.findByReplyId(replyId);
        nestedReplyRepository.deleteAll(NestedReplyList);
    }
}
