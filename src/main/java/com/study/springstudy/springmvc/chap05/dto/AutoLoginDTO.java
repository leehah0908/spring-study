package com.study.springstudy.springmvc.chap05.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
public class AutoLoginDTO {
    private String sessionId;
    private LocalDateTime limitTime;
    private String account;

}
