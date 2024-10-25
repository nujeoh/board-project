package com.example.boardproject.post.service;

import com.example.boardproject.post.db.PostEntity;
import com.example.boardproject.post.model.PageDto;
import com.example.boardproject.post.model.PostDto;
import com.example.boardproject.reply.service.ReplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostConverter {

    private final ReplyConverter replyConverter; // コメント関連のデータベース操作を処理するリポジトリ

    // PostEntity => PostDtoに変換
    public PostDto toDto(PostEntity postEntity){

        if(postEntity == null) return null;

        var replyList = postEntity.getReplyList().stream()
                .map(replyEntity -> {
                    return replyConverter.toDto(replyEntity);
                }).toList();

        return PostDto.builder()
                .id(postEntity.getId())
                .userId(postEntity.getUser().getId())
                .userName(postEntity.getUserName())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .hit(postEntity.getHit())
                .postedAt(postEntity.getPostedAt().toLocalDate())
                .replyList(replyList)
                .build()
                ;
    }

    // List<PostEntity> => List<PostDto>に変換
    public List<PostDto> toDtoList(List<PostEntity> postEntityList){

        var postDtoList = postEntityList.stream()
                .map(postEntity -> toDto(postEntity))
                .toList();

        return postDtoList;
    }

    // Page<PostEntity> => PageDtoに変換
    public PageDto PageToDto(Page<PostEntity> postEntityPage) {

        // PostEntityリストをPostDtoリストに変換
        List<PostDto> postDtoList = postEntityPage.getContent().stream()
                .map(postEntity -> toDto(postEntity)) // PostEntity を PostDto に変換
                .toList();

        // PageDtoビルダーを使用してオブジェクトを生成
        return PageDto.builder()
                .content(postDtoList)
                .page(postEntityPage.getNumber())
                .size(postEntityPage.getSize())
                .currentElements(postEntityPage.getNumberOfElements())
                .totalPages(postEntityPage.getTotalPages())
                .totalElements(postEntityPage.getTotalElements())
                .build();
    }
}
