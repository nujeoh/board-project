package com.example.boardproject.reply.service;

import com.example.boardproject.reply.db.ReplyEntity;
import com.example.boardproject.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyConverter {

    private final NestedReplyConverter nestedReplyConverter;

    // ReplyEntity => ReplyDtoに変換
    public ReplyDto toDto(ReplyEntity replyEntity) {

        var nestedReplyList = replyEntity.getNestedReplyList().stream()
                .map(nestedReplyEntity -> {
                    return nestedReplyConverter.toDto(nestedReplyEntity);
                }).toList();

        return ReplyDto.builder()
                .id(replyEntity.getId())
                .postId(replyEntity.getPost().getId())
                .userId(replyEntity.getUser().getId())
                .userName(replyEntity.getUserName())
                .content(replyEntity.getContent())
                .repliedAt(replyEntity.getRepliedAt())
                .nestedReplyEntityList(nestedReplyList)
                .build()
                ;
        }

    // List<ReplyEntity> => List<ReplyDto>に変換
    public List<ReplyDto> toDtoList(List<ReplyEntity> replyEntityList){

        var replyDtoList = replyEntityList.stream()
                .map(replyEntity -> toDto(replyEntity))
                .toList();

        return replyDtoList;
    }
}
