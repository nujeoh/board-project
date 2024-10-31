package com.example.boardproject.reply.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NestedReplyResponseDTO {
    // Entityの情報をDtoに変換（クライアントに提供するため）

    private Long id;

    private Long replyId;

    private Long userId;

    private String userName;

    private String content;

    private String status;

    private LocalDateTime repliedAt;
}
