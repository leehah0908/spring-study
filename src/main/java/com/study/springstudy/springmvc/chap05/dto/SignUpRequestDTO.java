package com.study.springstudy.springmvc.chap05.dto;

import com.study.springstudy.springmvc.chap05.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@ToString
@Builder
public class SignUpRequestDTO {

    @NotBlank
    @Size(min = 4, max = 14)
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 2)
    private String name;

    @NotBlank
    @Email // email 형식인지 검사 -> 모양만 검사하는거임
    private String email;

    public Member toEntity (PasswordEncoder encoder) {
        return Member.builder()
                .account(account)
                .password(encoder.encode(password)) // 비밀번호는 암호화해서 넘기기
                .email(email)
                .name(name)
                .build();
    }

}
