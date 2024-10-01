package com.study.springstudy.core.chap04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("western")
public class WesternRestaurant implements Restaurant {

    private Chef chef;
    private Course course;

    @Autowired
    public WesternRestaurant(@Qualifier("jann") Chef chef,
                             @Qualifier("french") Course course) {
        this.chef = chef;
        this.course = course;
    }

    @Override
    public void order() {
        System.out.println("안녕하세요 프랑스 레스토랑입니다.");
        chef.cook();
        course.combineMenu();
    }
}
