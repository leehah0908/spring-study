package com.study.springstudy.springmvc.interceptor;

import com.study.springstudy.springmvc.chap05.entity.Member;
import com.study.springstudy.springmvc.chap05.mapper.MemberMapper;
import com.study.springstudy.springmvc.chap05.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 자동 로그인 쿠키를 가진 클라이언트인지 확인
        Cookie cookie = WebUtils.getCookie(request, "auto");

        // 자동 로그인 쿠키가 있다면
        if (cookie != null) {

            // 쿠키의 세션 아이디 확인
            String sessionId = cookie.getValue();

            // DB에서 sessionId와 동일한 회원을 조회
            Member member = memberMapper.findByCookie(sessionId);

            // 회원이 정상적으로 조회가 되었고, 자동 로그인 만료시간 이전이라면 로그인 수행
            if (member != null && LocalDateTime.now().isBefore(member.getLimitTime())) {
                memberService.maintainLoginStatus(request.getSession(), member.getAccount());
            }
        }
        // 자동 로그인을 안했어도 다 서비스로 넘어가야 함
        return true;
    }
}
