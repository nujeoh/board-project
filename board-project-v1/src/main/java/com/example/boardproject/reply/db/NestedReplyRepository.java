package com.example.boardproject.reply.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NestedReplyRepository extends JpaRepository<NestedReplyEntity, Long> {

    // reply_idを通じて返信コメントリストを返す
    // select * form nested_reply where reply_id = :?
    List<NestedReplyEntity> findByReplyId(Long id);
}
