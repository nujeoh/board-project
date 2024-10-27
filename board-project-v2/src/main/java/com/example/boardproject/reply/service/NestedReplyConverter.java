package com.example.boardproject.reply.service;

import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.model.NestedReplyDto;
import org.springframework.stereotype.Service;

@Service
public class NestedReplyConverter {

    // NestedReplyEntity => NestedReplyDtoに変換
    public NestedReplyDto toDto(NestedReplyEntity nestedReplyEntity){

        return NestedReplyDto.builder()
                .id(nestedReplyEntity.getId())
                .replyId(nestedReplyEntity.getReply().getId())
                .userId(nestedReplyEntity.getUser().getId())
                .userName(nestedReplyEntity.getUserName())
                .content(nestedReplyEntity.getContent())
                .repliedAt(nestedReplyEntity.getRepliedAt())
                .build()
                ;
    }
}
