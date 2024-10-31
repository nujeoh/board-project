package com.example.boardproject.post.service;

import com.example.boardproject.post.db.PostEntity;
import com.example.boardproject.post.model.Pagination;
import com.example.boardproject.post.model.PostResponseDTO;
import com.example.boardproject.reply.service.ReplyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostConverter {

    private final ReplyConverter replyConverter; // EntityをDtoに変換するためのコンバータ

    // PostEntity => PostDtoに変換して返す
    public PostResponseDTO toDto(PostEntity postEntity){

        var replyList = postEntity.getReplyList().stream()
                .map(replyEntity -> {
                    return replyConverter.toDto(replyEntity);
                }).toList();

        return PostResponseDTO.builder()
                .id(postEntity.getId())
                .userId(postEntity.getUser().getId())
                .userName(postEntity.getUser().getName())
                .title(postEntity.getTitle())
                .content(postEntity.getContent().replace("\n", "<br/>"))
                .hit(postEntity.getHit())
                .postedAt(postEntity.getPostedAt().toLocalDate())
                .replyList(replyList)
                .build()
                ;
    }

    // Page<PostEntity> => PageDtoに変換して返す
    public Pagination PageToDto(Page<PostEntity> postEntityPage) {

        // PostEntityリストをPostDtoリストに変換
        List<PostResponseDTO> postResponseDTOList = postEntityPage.getContent().stream()
                .map(postEntity -> toDto(postEntity)) // PostEntity を PostDto に変換
                .toList();

        // PageDtoビルダーを使用してオブジェクトを生成
        return Pagination.builder()
                .content(postResponseDTOList)
                .page(postEntityPage.getNumber())
                .size(postEntityPage.getSize())
                .currentElements(postEntityPage.getNumberOfElements())
                .totalPages(postEntityPage.getTotalPages())
                .totalElements(postEntityPage.getTotalElements())
                .build();
    }
}
