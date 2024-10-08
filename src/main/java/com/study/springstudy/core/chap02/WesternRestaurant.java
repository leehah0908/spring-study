package com.study.springstudy.core.chap02;

public class WesternRestaurant implements Restaurant{

    private Chef chef = new JannChef();
    private Course course = new FrenchCourse();

    @Override
    public void order() {
        System.out.println("안녕하세요 프랑스 레스토랑입니다.");
        chef.cook();
        course.combineMenu();
    }
}
