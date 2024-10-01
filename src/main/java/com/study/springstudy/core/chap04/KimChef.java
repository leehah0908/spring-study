package com.study.springstudy.core.chap04;

import org.springframework.stereotype.Component;

@Component("kim")
public class KimChef implements Chef {

    @Override
    public void cook() {
        System.out.println("김셰프입니다.");
        System.out.println("요리를 시작하겠습니다.");
    }
}
