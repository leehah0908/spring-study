package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.dto.ScorePostDTO;
import com.study.springstudy.springmvc.chap03.entity.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// spring 컨테이너의 빈을 활용한 테스트 클래스를 활용
@SpringBootTest
class ScoreJdbcRepositoryTest {

    @Autowired
    ScoreJdbcRepository repository;

    @Test
    @DisplayName("새로운 성적 정보를 save를 통해 추가")
        // 테스트 확인을 위해 사용 -> 굳이 안써도 되긴 하는데 로그 확인하기에 좋음
    void saveTest() {
        ScorePostDTO dto = new ScorePostDTO("송성문", 50, 100, 70);
        Score score = new Score(dto);

        repository.save(score);
    }

    @Test
    @DisplayName("score 테이블의 모든 학생 목록을 조회했을 때의 학생수 확인")
    void findAllTest() {
        // given -> 테스트에 필요한 값을 세팅

        // when -> 테스트 주요 코드 실행
        List<Score> allList = repository.findAll();

        // then -> 테스트 결과 검증 (Assertion: 단언기법)
        assertEquals(allList.size(), 2);
    }

    @Test
    @DisplayName("2번 학생의 이름은 송성문일 것이다.")
    void findOneTest() {
        // given
        int stuNum = 2;

        // when
        Score score = repository.findOne(stuNum);

        // then
        if (score != null) {
            assertEquals(score.getStuName(), "송성문");
        }
//        } else {
//            assertNull(score);
//        }
    }

    @Test
    @DisplayName("1번 학생을 삭제하면, 1번 학생은 더 이상 조회되지 않음")
    void deleteTest() {
        // given
        int stuNum = 1;

        // when
        repository.delete(stuNum);
        Score score = repository.findOne(stuNum);

        // then
        assertNull(score);
    }
}