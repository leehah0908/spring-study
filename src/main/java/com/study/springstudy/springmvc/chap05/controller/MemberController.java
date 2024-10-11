package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
