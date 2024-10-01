package com.study.springstudy.core.chap01;

/**
 * @problem - 호텔 클래스에서 직접 객체를 생성하면
 *            나중에 의존객체(레스토랑, 헤드쉐프)를 변경해야 할 때
 *            직접 호텔 클래스를 수정해야 하므로 OCP를 위반하게 됨.
 *            그리고, headChef가 변경되면 레스토랑 안에 쉐프도
 *            같이 바뀌어야 할 때 2군데를 수정해야 함.
 */
public class Hotel {

    // 레스토랑
    private AsianRestaurant restaurant = new AsianRestaurant();


    // 메인 셰프
    private KimChef mainChef = new KimChef();


    // 호텔을 소개하는 기능
    public void inform() {
        System.out.printf("우리 호텔의 레스토랑은 %s입니다. 메인 셰프는 %s입니다. \n",
                restaurant.getClass().getSimpleName(),
                mainChef.getClass().getSimpleName());

        restaurant.orderMenu();
    }
}
