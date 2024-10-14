package com.study.springstudy.springmvc.interceptor;

import com.study.springstudy.springmvc.chap05.mapper.BoardMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

import static com.study.springstudy.springmvc.util.LoginUtils.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper mapper;

    // 컨트롤러로 요청이 들어가기 전에 실행할 내용
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 로그인하지 않은 유저는 글쓰기, 글 수정, 글 삭제 요청 막기
        HttpSession session = request.getSession();

        if (!isLogin(session)) {
            response.sendRedirect("/members/sign-in");
            return false;
        }

        // 삭제 요청이 들어왔을 떄 서버에서 작성자가 동일한지 확인
        // 현재 요청이 삭제 요청인지 확인
        String uri = request.getRequestURI();
        if (uri.contains("delete") || uri.contains("modify")) {
            // 관리자?
            if (isAdmin(session)) return true;

            // 작성자?
            // 삭제 요청이 들어온 글 번호 확인 -> DB조회 -> 작성자와 로그인 회원의 계정명 비교
            String boardNo = request.getParameter("boardNo");
            String writer = mapper.findOne(Integer.parseInt(boardNo)).getWriter();

            if (!isMine(session, writer)) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter w = response.getWriter();
                String htmlCode = "<script>\n" +
                        "    alert('본인이 작성한 게시글만 삭제가 가능합니다.');\n" +
                        "    location.href='/board/list';\n" +
                        "</script>";
                w.write(htmlCode);
                w.flush();
                return false;
            }
        }
        return true;
    }

    // 컨트롤러로 요청이 들어간 후 공통적으로 처리할 코드나 검사할 일들을 실행할 내용
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
