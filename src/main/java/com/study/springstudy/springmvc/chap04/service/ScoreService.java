package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.dto.ScorePostDTO;
import com.study.springstudy.springmvc.chap04.dto.ScoreResponseDTO;
import com.study.springstudy.springmvc.chap04.entity.Score;
import com.study.springstudy.springmvc.chap04.mapper.ScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// controller와 repository 사이에 위치하여 중간 처리 담당
// 프랜잭션 처리, 데이터 가공 등등...
// 의존 관계: Controller -> Service -> Repository (3 Tier Architecture)
@Service // component랑 같은 기능인데 명시적으로 service 사용
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreMapper repository;

    public List<ScoreResponseDTO> findAll(String sort) {
        /*
        컨트롤러는 데이터베이스에서 성적정보 리스트를 조회해 오기를 원하고 있다.
        그런데 데이터베이스는 민감한 정보까지 모두 조회한다.
        그리고 컬럼명도 그대로 노출하기 때문에 이 모든것을 숨기는 처리를 하고 싶다. -> response용 DTO를 생성하자!
        */

        // 일단 뽑아줘
        List<Score> scoreList = repository.findAll(sort);

//        List<ScoreResponseDTO> dtoList = new ArrayList<>();
//        for (Score score : scoreList) {
//            ScoreResponseDTO dto = new ScoreResponseDTO(score);
//            dtoList.add(dto);
//        }

        // 데이터 처리는 내가 할게
        return scoreList
                .stream()
                .map(ScoreResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void save(ScorePostDTO dto) {
        repository.save(new Score(dto));
    }

    // 원래라면 응답용 DTO를 생성하며 가공한 후 컨트롤러에게 넘기는 것이 원칙이지만 지금은 그냥 Entity Score를 넘기겠음
    public Score findOne(int num) {
        return repository.findOne(num);
    }

    public void remove(int stuNum) {
        repository.delete(stuNum);
    }

    public void update(ScorePostDTO dto, int stuNum) {
        Score changeScore = new Score(dto);
        changeScore.setStuNum(stuNum);
        repository.update(changeScore);
    }
}
