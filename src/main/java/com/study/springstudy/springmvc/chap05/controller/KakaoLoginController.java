package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.service.KakaoLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${sns.kakao.app-key}")
    private String client_id;

    @Value("${sns.kakao.redirect-uri}")
    private String redirect_uri;

    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        // 카카오 인가코드 신청 -> 카카오 인증 서버에서 클라이언트와 로그인 과정 거친 후 redirect URI로 요청을 보냄
        String uri = "https://kauth.kakao.com/oauth/authorize";
        uri += "?client_id=" + client_id;
        uri += "&redirect_uri=" + redirect_uri;
        uri += "&response_type=code";

        return "redirect:" + uri;
    }

    // 약속된 redirect uri로 인가 코드가 옴
    @GetMapping("/auth/kakao")
    public String authCodeKakao(@RequestParam String code,
                              HttpSession session) {

        // 인가 코드를 가지고 카카오 인증 서버에 토큰 발급을 요청 (server to server 통신)
        Map<String, String> params = new HashMap<>();
        params.put("appKey", client_id);
        params.put("redirect", redirect_uri);
        params.put("code", code);

        kakaoLoginService.login(params, session);

        // 로그인 처리 완료되면 홈으로 이동
        return "redirect:/board/list";


    }


}
