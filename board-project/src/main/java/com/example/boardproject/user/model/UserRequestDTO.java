package com.example.boardproject.user.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
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
public class UserRequestDTO {

    private Long id;

    @NotBlank(message = "account は Null または空白ではいけません。")
    @Size(min = 6, max= 20, message = "account が6~20文字以外ではいけません。")
    private String account;

    @NotBlank(message = "password は Null または空白ではいけません。")
    @Size(min = 6, max= 20, message = "password  が6~20文字以外ではいけません。")
    private String password;

    @NotBlank(message = "name は Null または空白ではいけません。")
    @Size(min = 1, max= 20, message = "name が1~20文字以外ではいけません。")
    private String name;

    @NotBlank(message = "phone は Null または空白ではいけません。")
    @Size(min = 10, max= 11, message = "phone が10~11文字以外ではいけません。")
    private String phone;

    @NotBlank(message = "email1 は Null または空白ではいけません。")
    @Size(min = 3, max= 20, message = "email1 が3~20文字以外ではいけません。")
    private String email1;

    @NotBlank(message = "email2 は Null または空白ではいけません。")
    private String email2;
}
