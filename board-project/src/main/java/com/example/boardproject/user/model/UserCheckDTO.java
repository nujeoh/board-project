package com.example.boardproject.user.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserCheckDTO {

    @NotBlank(message = "account は Null または空白ではいけません。")
    @Size(min = 6, max= 20, message = "account が6~20文字以外ではいけません。")
    private String account;

    @NotBlank(message = "password は Null または空白ではいけません。")
    @Size(min = 6, max= 20, message = "password  が6~20文字以外ではいけません。")
    private String password;
}
