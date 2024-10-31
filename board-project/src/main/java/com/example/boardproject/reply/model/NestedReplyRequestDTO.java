package com.example.boardproject.reply.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NestedReplyRequestDTO {

    @NotNull(message = "replyId が Null ではいけません。")
    private Long replyId;

    @NotNull(message = "userId が Null ではいけません。")
    private Long userId;

    @NotBlank(message = "content が Null または空白ではいけません。")
    @Size(min = 1, max = 500, message = "content が1~500文字以外ではいけません。")
    private String content;
}
