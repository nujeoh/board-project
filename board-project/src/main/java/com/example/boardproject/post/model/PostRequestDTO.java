package com.example.boardproject.post.model;

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
public class PostRequestDTO {

    @NotNull(message = "userId が Null ではいけません。")
    private Long userId;

    @NotBlank(message = "userName が Null または空白ではいけません。")
    private String userName;

    @NotBlank(message = "title が Null または空白ではいけません。")
    @Size(min = 1, max = 50, message = "title が1~50文字以外ではいけません。")
    private String title;

    @NotBlank(message = "content が Null または空白ではいけません。")
    @Size(min = 1, max = 1000, message = "content が1~10000文字以外ではいけません。")
    private String content;
}
