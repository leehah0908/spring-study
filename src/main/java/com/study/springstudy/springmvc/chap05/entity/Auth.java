package com.study.springstudy.springmvc.chap05.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Auth {

    COMMON("일반 회원,", 1),
    ADMIN("관리자", 2);

    private String desc;
    private int authNumber;
}
