package com.example.boardproject.reply.db;

import com.example.boardproject.user.db.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "nested_reply") // データベースのnested_replyというテーブルとマッチングしてマッピング
public class NestedReplyEntity {

    @Id // primary keyに設定
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary keyの生成をデータベースに委任 - MySQL auto_increment
    private Long id;

    @ManyToOne // 連関関係マッピング
    private ReplyEntity reply; // nested_replyテーブルのreply_idというcolumnを通じてReplyEntity(コメント情報)を満たす

    @ManyToOne // 連関関係マッピング
    private UserEntity user;  // nested_replyテーブルのuser_idというcolumnを通じてUserEntity(コメント情報)を満たす

    private String userName;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime repliedAt;
}
