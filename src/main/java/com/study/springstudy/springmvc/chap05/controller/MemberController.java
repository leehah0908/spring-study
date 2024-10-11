package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.dto.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private final MemberService service;

    @GetMapping("/sign-up")
    public void signUp() {

    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam String type,
                                   @RequestParam String keyword) {
        boolean flag = service.checkIdentifier(type, keyword);
        return ResponseEntity.ok().body(flag);
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated SignUpRequestDTO dto) {
        boolean flag = service.join(dto);

        return flag ? "redirect:/members/sign-in" : "redirect:/members/sign-up";

    }
}
