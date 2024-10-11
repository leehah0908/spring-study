package com.study.springstudy.springmvc.chap05.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class LoginRequestDTO {

    private String account;
    private String password;
    private Boolean autoLogin; // null을 받을 수 있는 boolean

}
