package com.study.springstudy.springmvc.chap03.dto;

import com.study.springstudy.springmvc.chap03.entity.Grade;
import com.study.springstudy.springmvc.chap03.entity.Score;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ScoreModifyDTO {

    private final int stuNum;
    private final int kor;
    private final int eng;
    private final int math;

    public ScoreModifyDTO(Score score) {
        this.stuNum = score.getStuNum();
        this.kor = score.getKor();
        this.eng = score.getEng();
        this.math = score.getMath();
    }
}
