package com.study.springstudy.springmvc.chap04.dto;

import lombok.*;

// 역할: 브라우저가 요청과 함께 전달한 성적 정보를 포장하는 객체
@Setter @Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScorePostDTO {
    // 넘어오는 변수명(jsp 파일에서 사용하는 변수명)과 똑같이 설정 (SQL, Entity에서 쓰는거 X)
    private String name;
    private int kor;
    private int eng;
    private int math;
}
