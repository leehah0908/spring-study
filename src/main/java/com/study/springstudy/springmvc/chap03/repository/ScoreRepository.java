package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;

import java.util.List;

// 역할: 적당한 저장소에 CRUD하는 역할
// 얘가 자체적으로 하는 게 아닌, 기능만 명세
// 다양한 DB를 사용할 수 있기 때문에 interface로 만드는 것이 좋음
public interface ScoreRepository {

    // 저장소에 데이터 추가하기
    // 저장은 리턴값이 없기 때문에 void로 선언
    void save(Score score);

    // 저장소에서 데이터 전체조회하기
    List<Score> findAll(String sort);

    // 저장소에서 데이터 개별조회하기
    Score findOne(int stuNum);

    // 저장소에서 데이터 삭제하기
    void delete(int stuNum);

    // 저장소에서 데이터 업데이트하기
    void update(Score score);
}
