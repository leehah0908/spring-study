package com.study.springstudy.chap01;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 커맨드 객체 생성 -> 요청과 함께 전달되는 데이터가 많을 경우 스프링에게 객체 형태로 파라미터를 전달해달라고 요구
@Getter @Setter
@ToString
public class OrderDTO {

    // 필드 선언은 무조건 은닉으로 선언해줘야 하고, 쿼리 파라미터 변수명과 동일해야 함
    // 또한 반드시 getter와 setter를 구현하기 위해 정보 은닉을 구현해야 함
    private int orderNum;
    private String goods;
    private int amount;
    private int price;
}
