package com.example.boardproject.reply.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReplyResponseDTO {
    // Entityの情報をDtoに変換（クライアントに提供するため）

    private Long id;

    private Long postId;

    private Long userId;

    private String userName;

    private String content;

    private String status;

    private LocalDateTime repliedAt;

    private List<NestedReplyResponseDTO> nestedReplyEntityList = List.of();
}
