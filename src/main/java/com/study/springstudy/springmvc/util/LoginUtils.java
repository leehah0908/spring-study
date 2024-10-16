package com.study.springstudy.springmvc.util;

import com.study.springstudy.springmvc.chap05.dto.response.LoginUserResponseDTO;
import jakarta.servlet.http.HttpSession;

import static com.study.springstudy.springmvc.chap05.entity.Auth.ADMIN;

// 회원 인증, 인가와 관련된 상수와 메서드를 가진 클래스
public class LoginUtils {

    // 로그인 세션 키
    public static final String LOGIN_KEY = "login";

    // 로그인 여부 확인
    public static boolean isLogin(HttpSession session) {
        return session.getAttribute(LOGIN_KEY) != null;
    }

    // 로그인한 사람의 계정명을 반환해주는 메서드
    public static String getCurrentLoginMember(HttpSession session) {

        // session.getAttribute()의 리턴값이 Object이기 때문에 자식 고유의 속성 및 기능을 사용하기 위해서는 타입 변환 필요
        LoginUserResponseDTO dto = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);

        return dto.getAccount();
    }

    public static boolean isAdmin(HttpSession session) {
        LoginUserResponseDTO dto = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);

        return dto.getAuth().equals(ADMIN.toString());
    }

    public static boolean isMine(HttpSession session, String targetAccount) {
        return targetAccount.equals(getCurrentLoginMember(session));
    }
}
