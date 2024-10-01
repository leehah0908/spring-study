package com.study.springstudy.core.chap04;

import org.springframework.stereotype.Component;

@Component("jann")
public class JannChef implements Chef {

    @Override
    public void cook() {
        System.out.println("장셰프입니다.");
        System.out.println("요리를 시작하겠습니다.");
    }
}
