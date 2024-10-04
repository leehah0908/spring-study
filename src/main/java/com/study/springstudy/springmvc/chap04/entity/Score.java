package com.study.springstudy.springmvc.chap04.entity;

import com.study.springstudy.springmvc.chap04.dto.ScorePostDTO;
import lombok.*;

/*
create table score (
    stu_num int primary key auto_increment,
    stu_name varchar(30) not null,
    kor int not null default 0,
    eng int not null default 0,
    math int not null default 0,
    total int not null default 0,
    average float not null,
    grade varchar(1)
);
*/

// 역할: 데이터베이스의 테이블의 컬럼과 1:1로 매칭되는 필드를 가진 객체
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private int stuNum; // insert에서는 신경쓰지 않아도 됨 (자동 생성)
    private String stuName;
    private int kor;
    private int eng;
    private int math;
    private int total; // insert에서는 신경쓰지 않아도 됨 (로직에서 계산)
    private double average; // insert에서는 신경쓰지 않아도 됨 (로직에서 계산)
    private Grade grade; // insert에서는 신경쓰지 않아도 됨 (로직에서 계산)

    public Score(ScorePostDTO dto) {
        convertInputData(dto);
        calculate();
        makeGrade();
    }

    // 전달되는 dto에서 필요한 데이터를 Score의 필드에 할당하는 메서드
    private void convertInputData(ScorePostDTO dto) {
        this.stuName = dto.getName();
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
    }

    // 총점과 평균을 계산하는 메서드
    private void calculate() {
        this.total = this.kor + this.eng + this.math;
        this.average = this.total / 3.0;
    }

    // 평균에 따른 학점 부여
    private void makeGrade() {
        if (this.average >= 90) this.grade = Grade.A;
        else if (this.average >= 80) this.grade = Grade.B;
        else if (this.average >= 70) this.grade = Grade.C;
        else if (this.average >= 60) this.grade = Grade.D;
        else this.grade = Grade.F;
    }
}