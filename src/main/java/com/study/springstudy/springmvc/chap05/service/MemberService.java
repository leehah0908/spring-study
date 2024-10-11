package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.LoginRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.response.LoginUserResponseDTO;
import com.study.springstudy.springmvc.chap05.entity.Member;
import com.study.springstudy.springmvc.chap05.mapper.MemberMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.study.springstudy.springmvc.chap05.service.LoginResult.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원가입 처리 서비스
    public boolean join(SignUpRequestDTO dto) {
        return memberMapper.save(dto.toEntity(encoder));
    }

    // 로그인 요청
    // 아이디가 틀렸는지 비밀번호가 틀렸는지 알려주지 않을 때는 boolean으로 return 줘도 됨 -> 이걸 추천
    public LoginResult authenticate(LoginRequestDTO dto) {

        Member findMember = memberMapper.findOne(dto.getAccount());

        // 가입 여부 확인 (아이디 확인)
        if (findMember == null) {
            return NO_ACC;
        }

        // 비밀번호 일치 검사
        // 둘 다 맞으면 성공
        // encoder.matches(): DB에서도 모름 -> 그래서 비밀번호 찾으면 새로운 비밀번호 입력하라고 하거나 임시 비밀번호를 알려주는거임
        if (encoder.matches(dto.getPassword(), findMember.getPassword())) {
            return SUCCESS;
        } else return NO_PW;
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
                .build();

        // 세션에 로그인한 회원 정보 저장
        session.setAttribute("login", dto);

        // 세션 수명 설정
        session.setMaxInactiveInterval(60 * 60);
    }
}
