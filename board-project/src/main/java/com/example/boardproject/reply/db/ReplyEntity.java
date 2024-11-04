package com.example.boardproject.reply.db;

import com.example.boardproject.post.db.PostEntity;
import com.example.boardproject.user.db.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "reply") // データベースのreplyというテーブルとマッチングしてマッピング
public class ReplyEntity {

    @Id // primary keyに設定
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary keyの生成をデータベースに委任 - MySQL auto_increment
    private Long id;

    @ManyToOne // 連関関係マッピング
    private PostEntity post; // replyテーブルのpost_idというcolumnを通じてPostEntity(コメント情報)を満たす
    
    @ManyToOne // 連関関係マッピング
    private UserEntity user; // replyテーブルのuser_idというcolumnを通じてUserEntity(コメント情報)を満たす

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime repliedAt;

    @OneToMany(mappedBy = "reply") // 連関関係マッピング
    @Builder.Default
    private List<NestedReplyEntity> nestedReplyList = List.of(); // NestedReplyEntityと双方向マッピングされ, 照会することだけ可能
}
