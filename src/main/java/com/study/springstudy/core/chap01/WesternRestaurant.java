package com.study.springstudy.core.chap01;

public class WesternRestaurant {

    private JannChef chef = new JannChef();
    private FrenchCourse course = new FrenchCourse();

    public void orderMenu() {
        System.out.println("안녕하세요 프랑스 레스토랑입니다.");
        chef.cook();
        course.combineMenu();
    }
}
