package com.example.boardproject.post.db;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // ビューをアップデート
    // update into post set hit = hit + 1 where id = :?
    @Transactional
    @Modifying
    @Query(
            value = "update post set hit = hit + 1 where id = :id",
            nativeQuery = true
    )
    int updateHit(Long id);

    // 投稿のタイトルに検索するリストを返す
    // select * from post where title like '%:?%' order by id desc
    Page<PostEntity> findByTitleContainsOrderByIdDesc(String title, Pageable pageable);

    // 投稿の内容に検索するリストを返す
    // select * from post where content like '%:?%' order by id desc
    Page<PostEntity> findByContentContainsOrderByIdDesc(String search, Pageable pageable);
}
