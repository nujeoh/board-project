package com.example.boardproject.reply.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    // post_idを通じコメントリストを返す
    // select * form reply where post_id = :?
    List<ReplyEntity> findByPostId(Long postId);
}
