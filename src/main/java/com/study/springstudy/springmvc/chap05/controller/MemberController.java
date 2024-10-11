package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.dto.request.LoginRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.service.LoginResult;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private final MemberService service;

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam String type,
                                   @RequestParam String keyword) {
        boolean flag = service.checkIdentifier(type, keyword);
        return ResponseEntity.ok().body(flag);
    }

    @GetMapping("/sign-up")
    public void signUp() {
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated SignUpRequestDTO dto) {
        boolean flag = service.join(dto);

        return flag ? "redirect:/members/sign-in" : "redirect:/members/sign-up";
    }

    @GetMapping("/sign-in")
    public void signIn() {
    }

    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto,
                         RedirectAttributes ra) { // 모델에 담긴 데이터는 redirect시 전달이 안됨 -> RedirectAttributes를 사용해야 함
        LoginResult result = service.authenticate(dto);
        // 데이터를 일회성으로 전달할 때 사용하는 메서드
        ra.addFlashAttribute("result", result);

        if (result == LoginResult.SUCCESS) {
            return "redirect:/board/list";
        }
        return "redirect:/members/sign-in";
    }
}
