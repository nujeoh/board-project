package com.example.boardproject.post.model;

import com.example.boardproject.reply.model.ReplyDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostDto {
    // Entityの情報をDtoに変換（クライアントに提供するため）

    private Long id;

    private Long userId;

    private String userName;

    private String title;

    private String content;

    private Integer hit;

    private String status;

    private LocalDate postedAt;

    private List<ReplyDto> replyList = List.of();
}
