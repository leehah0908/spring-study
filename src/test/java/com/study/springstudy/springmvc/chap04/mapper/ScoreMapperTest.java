package com.study.springstudy.springmvc.chap04.mapper;

import com.study.springstudy.springmvc.chap04.dto.ScorePostDTO;
import com.study.springstudy.springmvc.chap04.entity.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ScoreMapperTest {

    @Autowired
    ScoreMapper mapper;

    @Test
    @DisplayName("mybatis insert 테스트")
    void insertTest() {
        // given
        Score score = new Score(new ScorePostDTO("김혜성", 95, 89, 90));

        // when
        mapper.save(score);

        // then

    }

    @Test
    @DisplayName("mybatis select 테스트")
    void selectTest() {
        // given

        // when
        List<Score> scoreList = mapper.findAll("num");

        // then
        scoreList.forEach(System.out::println);

    }

    @Test
    @DisplayName("mybatis select 테스트")
    void selectOneTest() {
        // given

        // when
        Score score = mapper.findOne(7);

        // then
        System.out.println(score);

    }

    @Test
    @DisplayName("mybatis select 테스트")
    void selectSortTest() {
        // given

        // when
        List<Score> list = mapper.findAll("name");

        // then
        list.forEach(System.out::println);

    }

}