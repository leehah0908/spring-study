package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.AutoLoginDTO;
import com.study.springstudy.springmvc.chap05.dto.request.LoginRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.response.LoginUserResponseDTO;
import com.study.springstudy.springmvc.chap05.entity.Member;
import com.study.springstudy.springmvc.chap05.mapper.MemberMapper;
import com.study.springstudy.springmvc.util.LoginUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;

import static com.study.springstudy.springmvc.chap05.service.LoginResult.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원가입 처리 서비스
    public boolean join(SignUpRequestDTO dto, String savePath) {
        return memberMapper.save(dto.toEntity(encoder, savePath));
    }

    // 로그인 요청
    // 아이디가 틀렸는지 비밀번호가 틀렸는지 알려주지 않을 때는 boolean으로 return 줘도 됨 -> 이걸 추천
    public LoginResult authenticate(LoginRequestDTO dto,
                                    HttpSession session,
                                    HttpServletResponse response) {

        Member findMember = memberMapper.findOne(dto.getAccount());

        // 가입 여부 확인 (아이디 확인)
        if (findMember == null) {
            return NO_ACC;
        }

        // 비밀번호 일치 검사
        // 둘 다 맞으면 성공
        // encoder.matches(): DB에서도 모름 -> 그래서 비밀번호 찾으면 새로운 비밀번호 입력하라고 하거나 임시 비밀번호를 알려주는거임
        if (!encoder.matches(dto.getPassword(), findMember.getPassword())) {
            return NO_PW;
        }

        // 자동 로그인 처리
        if (dto.getAutoLogin()) {
            // 자동 로그인 쿠키 생성 -> 쿠키 내용은 중복되지 않는 값(브라우저의 세션 아이디)을 저장
            Cookie cookie = new Cookie("auto", session.getId());

            // 쿠키 설정
            int limitTime = 60 * 60 * 24 * 7;
            cookie.setPath("/");
            cookie.setMaxAge(limitTime);

            // 클라이언트에 쿠키 담아서 전송
            response.addCookie(cookie);

            // DB에 쿠키에 관련된 값들(랜덤 세션 아이디, 자동 로그인 만료시간)을 갱신
            AutoLoginDTO autoDto = AutoLoginDTO.builder()
                    .sessionId(session.getId())
                    .limitTime(LocalDateTime.now().plusSeconds(limitTime))
                    .account(dto.getAccount())
                    .build();
            memberMapper.saveAutoLogin(autoDto);
        }
        return SUCCESS;
    }

    public boolean checkIdentifier(String type, String keyword) {
        return memberMapper.existsById(type, keyword);
    }

    public void maintainLoginStatus(HttpSession session, String account) {
        // 세션은 서버에서만 유일하게 보관되는 데이터로서 로그인 유지 등 상태 유지가 필요할 때 사용되는 내장 객체입니다.
        // 세션은 쿠키와 달리 모든 데이터를 저장할 수 있으며 크기도 제한이 없습니다.
        // 세션의 수명은 기본 1800초 -> 원하는 만큼 수명을 설정할 수 있습니다.
        // 브라우저가 종료되면 남은 수명에 상관없이 세션 데이터는 소멸합니다.

        // 현재 로그인한 회원의 모든 정보 조회
        Member findMember = memberMapper.findOne(account);

        // DB 데이터를 사용할 것만 정제
        LoginUserResponseDTO dto = LoginUserResponseDTO
                .builder()
                .account(findMember.getAccount())
                .name(findMember.getName())
                .email(findMember.getEmail())
                .auth(findMember.getAuth().toString())
                .build();

        // 세션에 로그인한 회원 정보 저장
        session.setAttribute(LoginUtils.LOGIN_KEY, dto);

        // 세션 수명 설정
        session.setMaxInactiveInterval(60 * 60);
    }

    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {
        Cookie c = WebUtils.getCookie(request, "auto");

        // 쿠키 삭제 -> 쿠키 수명 0으로 만들어서 클라이언트에 전송
        if (c != null) {
            c.setMaxAge(0);
            c.setPath("/");
            response.addCookie(c);
        }

        // DB 초기화
        memberMapper.saveAutoLogin(
                AutoLoginDTO.builder()
                        .sessionId("none")
                        .limitTime(LocalDateTime.now())
                        .account(LoginUtils.getCurrentLoginMember(request.getSession()))
                        .build());

    }
}
