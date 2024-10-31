package com.example.boardproject.reply.service;

import com.example.boardproject.reply.db.NestedReplyEntity;
import com.example.boardproject.reply.model.NestedReplyResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class NestedReplyConverter {

    // NestedReplyEntity => NestedReplyDtoに変換して返す
    public NestedReplyResponseDTO toDto(NestedReplyEntity nestedReplyEntity){

        return NestedReplyResponseDTO.builder()
                .id(nestedReplyEntity.getId())
                .replyId(nestedReplyEntity.getReply().getId())
                .userId(nestedReplyEntity.getUser().getId())
                .userName(nestedReplyEntity.getUserName())
                .content(nestedReplyEntity.getContent().replace("\n", "<br/>"))
                .repliedAt(nestedReplyEntity.getRepliedAt())
                .build()
                ;
    }
}
