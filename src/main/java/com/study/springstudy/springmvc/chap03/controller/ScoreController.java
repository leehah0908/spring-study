package com.study.springstudy.springmvc.chap03.controller;

import com.study.springstudy.springmvc.chap03.dto.ScorePostDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/score")
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

    @GetMapping("/list")
    public String list() {
        System.out.println("/score/list: GET!");
        return "score/score-list";
    }

    @PostMapping("/register")
    public String register(ScorePostDTO dto) {
        System.out.println("/score/register: POST!");
        System.out.println("dto = " + dto);
        return "score/score-list";
    }
}

