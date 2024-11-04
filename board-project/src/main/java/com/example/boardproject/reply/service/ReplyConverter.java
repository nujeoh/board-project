package com.example.boardproject.reply.service;

import com.example.boardproject.reply.db.ReplyEntity;
import com.example.boardproject.reply.model.ReplyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyConverter {

    private final NestedReplyConverter nestedReplyConverter; // EntityをDtoに変換するためのコンバータ

    // ReplyEntity => ReplyDtoに変換して返す
    public ReplyResponseDTO toDto(ReplyEntity replyEntity) {

        var nestedReplyList = replyEntity.getNestedReplyList().stream()
                .map(nestedReplyEntity -> {
                    return nestedReplyConverter.toDto(nestedReplyEntity);
                }).toList();

        return ReplyResponseDTO.builder()
                .id(replyEntity.getId())
                .postId(replyEntity.getPost().getId())
                .userId(replyEntity.getUser().getId())
                .userName(replyEntity.getUser().getName())
                .content(replyEntity.getContent().replace("\n", "<br/>"))
                .repliedAt(replyEntity.getRepliedAt())
                .nestedReplyEntityList(nestedReplyList)
                .build()
                ;
        }

    // List<ReplyEntity> => List<ReplyDto>に変換して返す
    public List<ReplyResponseDTO> toDtoList(List<ReplyEntity> replyEntityList){

        var replyDtoList = replyEntityList.stream()
                .map(replyEntity -> toDto(replyEntity))
                .toList();

        return replyDtoList;
    }
}
