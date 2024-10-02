package com.study.springstudy.springmvc.chap03.dto;

import com.study.springstudy.springmvc.chap03.entity.Grade;
import com.study.springstudy.springmvc.chap03.entity.Score;
import lombok.*;

// 응답용 DTO는 Setter를 구현하지 않음 -> 값의 변화가 발생하면 안됨
//@Setter
@Getter
@ToString
//@RequiredArgsConstructor -> final 필드 초기화하는 생성자
// 서버가 클라이언트에게 데이터를 전달할 때 보여줄 데이터만 선별해서 응답하도록 사용할 객체
// 쓸데없는 데이터나 민감한 정보가 들어있는 데이터는 전달할 필요가 없음
public class ScoreResponseDTO {

    private final int stuNum;
    private final String maskingName;
    private final double average;
    private final Grade grade;

    // 생성자의 매개값으로 Score 원형 객체가 전달되면 필요한 데이터만 뽑아서 DTO로 만들기
    public ScoreResponseDTO(Score score) {
        this.stuNum = score.getStuNum();
        this.average = score.getAverage();
        this.grade = score.getGrade();
        this.maskingName = makeMaskingName(score.getStuName());
    }

    // 학생의 성만 빼고 나머지 이름을 *로 가려주는 메서드
    private String makeMaskingName(String stuName) {
        // String과 합쳐지면 자동으로 String이 되기 때문에 String.valueOf를 할 필요가 없음
        return stuName.charAt(0) + "*".repeat(stuName.length() - 1);
    }
}
