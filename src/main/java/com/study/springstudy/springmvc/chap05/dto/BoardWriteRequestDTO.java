package com.study.springstudy.springmvc.chap05.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteRequestDTO {
    private String writer;
    private String title;
    private String content;
}
