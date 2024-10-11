package com.study.springstudy.springmvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

import static com.study.springstudy.springmvc.util.LoginUtils.isLogin;

@Configuration
public class AfterLoginInterceptor implements HandlerInterceptor {

    // 로그인한 이후에 비회원만 접근할 수 있는 페이지(회원가입, 로그인 페이지) 접근 차단
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 세션 받아오기 <- 인터페이스를 사용했기 때문에 주는대로 사용해야 함 (HttpSession 사용 못함)
        HttpSession session = request.getSession();

        // 세션에 데이터가 존재하지 않으면 null이 리턴
        if (isLogin(session)) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter w = response.getWriter();
            String htmlCode = "<!DOCTYPE html>\n" +
                    "<html lang=\"ko\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <title>Document</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "  <script>\n" +
                    "    alert('이미 로그인 한 회원입니다.');\n" +
                    "    location.href='/';\n" +
                    "  </script>\n" +
                    "  \n" +
                    "</body>\n" +
                    "</html>";
            w.write(htmlCode);
            w.flush();

            return false; // 컨트롤러로 들어가는 요청 막기
        }
        return true;
    }
}
