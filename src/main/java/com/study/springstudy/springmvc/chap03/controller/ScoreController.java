package com.study.springstudy.springmvc.chap03.controller;

import com.study.springstudy.springmvc.chap03.dto.ScoreModifyDTO;
import com.study.springstudy.springmvc.chap03.dto.ScorePostDTO;
import com.study.springstudy.springmvc.chap03.dto.ScoreResponseDTO;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.repository.ScoreJdbcRepository;
import com.study.springstudy.springmvc.chap03.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    /*
        # 요청 URL
        1. 학생 성적정보 등록화면 및 성적정보 목록조회 처리
        - /score/list : GET
        2. 성적 정보 등록 처리 요청
        - /score/register : POST
        3. 성적정보 삭제 요청
        - /score/remove : POST
        4. 성적정보 상세 조회 요청
        - /score/detail : GET
    */

    // repository를 사용하기 위해서 bean으로 등록해 놓은걸 가져오기
    private final ScoreService service;

    // 하나 조회할 때 사용하는 메서드
    private void retrieve(int num, Model model) {
        model.addAttribute("stu", service.findOne(num));
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "num") String sort,
                       Model model) {
        System.out.println("/score/list: GET!");

        model.addAttribute("sList", service.findAll(sort));

        return "score/score-list";
    }

    @PostMapping("/register")
    public String register(ScorePostDTO dto) {
        System.out.println("/score/register: POST!");
        System.out.println("dto = " + dto);

        service.save(dto);

        // 등록이 완료되었다면 목록 화면으로 데이터를 전달해서 점수 목록을 보여주고 싶음
        // 목록 처리를 다른 메서드가 하고 있음 -> 중복 로직을 작성할 필요가 없음
        // redirect를 통해 /score/list라는 요청이 다시 들어오게 유도
        return "redirect:/score/list";
    }

    // 성적 상세 조회 요청
    @GetMapping("/detail")
    public String detail(@RequestParam int num,
                         Model model) {
        System.out.println("/score/detail: GET!");

        retrieve(num, model);
        return "score/score-detail";
    }

    // 성적 삭제 요청
    @PostMapping("/delete")
    public String remove(@RequestParam int stuNum) {
        System.out.println("/score/delete: POST!");
        System.out.println("stuNum = " + stuNum);

        service.remove(stuNum);
        return "redirect:/score/list";
    }

    // 수정 페이지로 이동 요청
    @GetMapping("/modify")
    public String modify(@RequestParam int num,
                         Model model) {
        System.out.println("/score/modify: GET!");

        retrieve(num, model);
        return "score/score-modify";
    }

    // 수정값으로 값 수정(db update)
    // 수정이 완료되면 수정된 학생의 상세 페이지를 보여줘야 함
    // GetMapping랑 PostMapping이기 때문에 경로랑 메서드 이름이 같아도 상관없음!
    @PostMapping("/modify")
    public String modify(ScorePostDTO dto, // kor, math, eng는 dto로 받음
                         @RequestParam int stuNum) { // stuNum은 dto가 못받으니까 따로 받음

        service.update(dto, stuNum);
        return "redirect:/score/detail?num=" + stuNum;
    }


}

