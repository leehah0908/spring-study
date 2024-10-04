package com.study.springstudy.springmvc.chap04.mapper;

import com.study.springstudy.springmvc.chap04.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// MyBatis의 SQL 실행을 위한 인터페이스임을 명시
@Mapper
public interface ScoreMapper {

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
