package com.study.springstudy.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ResponseController {

    // jsp 파일로 포워딩할 때 데이터 전달하기
    // 1. Model 객체 사용하기 (더 많이 사용하긴 함)
    @RequestMapping("/hobbies")
    public String hobbies(Model model) {
        model.addAttribute("name", "홍길동");
        model.addAttribute("hobbies", List.of("축구", "캠핑", "백패킹", "러닝"));
        model.addAttribute("age", 27);

        return "mvc/hobbies";
    }

    // 2. ModelAndView 객체 사용하기 (REST API를 사용할 때는 이걸 사용함)
    @RequestMapping("/hobbies2")
    public ModelAndView hobbies2() {
        ModelAndView mv = new ModelAndView("mvc/hobbies");

        mv.addObject("name", "김철수");
        mv.addObject("hobbies", List.of("카페가기", "맛집가기"));

        return mv;
    }

}
