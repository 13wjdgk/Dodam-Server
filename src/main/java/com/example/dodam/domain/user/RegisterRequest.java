package com.example.dodam.domain.user;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    private String phone;
    private LocalDate birthDate;
    private String imgPath;

    public User toUser() {
        return User.builder()
            .email(email)
            .password(password)
            .phone(phone)
            .nickname(nickname)
            .birthDate(birthDate)
            .imgPath(imgPath)
            .build();
    }
}
