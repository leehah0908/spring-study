package com.study.springstudy.springmvc.chap03.entitiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 역할: 데이터베이스의 테이블의 컬럼과 1:1로 매칭되는 필드를 가진 객체
@Getter @Setter
@ToString
public class Score {
    private int stuNum; // insert에서는 신경쓰지 않아도 됨 (자동 생성)
    private String stuName;
    private int kor;
    private int eng;
    private int math;
    private int total; // insert에서는 신경쓰지 않아도 됨 (로직에서 계산)
    private double average; // insert에서는 신경쓰지 않아도 됨 (로직에서 계산)
    private Grade grade; // insert에서는 신경쓰지 않아도 됨 (로직에서 계산)
}