package com.example.boardproject.post.db;

import com.example.boardproject.reply.db.ReplyEntity;
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
@Entity(name = "post")  // データベースのpostというテーブルとマッチングしてマッピング
public class PostEntity {

    @Id // primary keyに設定
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary keyの生成をデータベースに委任 - MySQL auto_increment
    private Long id;

    @ManyToOne // 連関関係マッピング
    private UserEntity user; // postテーブルのuser_idというcolumnを通じてUserEntity(コメント情報)を満たす

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer hit;

    private LocalDateTime postedAt;

    @OneToMany(mappedBy = "post") // 連関関係マッピング
    @Builder.Default
    private List<ReplyEntity> replyList = List.of(); // ReplyEntityと双方向マッピングされ, 照会することだけ可能
}
