package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.dto.request.LoginRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.service.LoginResult;
import com.study.springstudy.springmvc.chap05.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
                         RedirectAttributes ra,
                         HttpServletResponse response,
                         HttpServletRequest request) { // 모델에 담긴 데이터는 redirect시 전달이 안됨 -> RedirectAttributes를 사용해야 함
        LoginResult result = service.authenticate(dto);
        // 데이터를 일회성으로 전달할 때 사용하는 메서드
        ra.addFlashAttribute("result", result);

        if (result == LoginResult.SUCCESS) {
            // 쿠키를 사용해서 로그인 상태 유지
//            makeLoginCookie(dto, response);

            // 세션을 사용해서 로그인 상태 유지
            // 서비스에 세션 객체와 아이디 전달
            service.maintainLoginStatus(request.getSession(), dto.getAccount());

            return "redirect:/board/list";
        }
        return "redirect:/members/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {

        // 세션에서 로그인 정보 기록 삭제
        session.removeAttribute("login");

        // 세선 전체 무효화 (둘 중 하나 선택해서 사용)
        session.invalidate();

        return "redirect:/board/list";
    }

    private void makeLoginCookie(LoginRequestDTO dto, HttpServletResponse response) {
        // 쿠키에 로그인 기록 저장
        // 쿠키 객체 생성 -> 쿠키의 이름과 저장할 값 포함 (문자열만 가능하고, 4KB 제한)
        Cookie cookie = new Cookie("login", dto.getAccount());

        // 쿠키 소멸 설정
        cookie.setMaxAge(60); // 쿠키 수명 설정 (초)
        cookie.setPath("/"); // 이 쿠키는 모든 경로애서 유효함

        // 쿠키를 응답 객체에 쿠키를 포함해서 클라이언트로 전송
        response.addCookie(cookie);
    }
}
