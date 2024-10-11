package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.SignUpRequestDTO;
import com.study.springstudy.springmvc.chap05.entity.Member;
import com.study.springstudy.springmvc.chap05.mapper.MemberMapper;
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
    public LoginResult authenticate(String account, String password) {

        Member findMember = memberMapper.findOne(account);

        // 가입 여부 확인 (아이디 확인)
        if (findMember == null) {
            return NO_ACC;
        }

        // 비밀번호 일치 검사
        // 둘 다 맞으면 성공
        // encoder.matches(): DB에서도 모름 -> 그래서 비밀번호 찾으면 새로운 비밀번호 입력하라고 하거나 임시 비밀번호를 알려주는거임
        if (encoder.matches(password, findMember.getPassword())) {
            return SUCCESS;
        } else return NO_PW;
    }

    public boolean checkIdentifier(String type, String keyword) {
        return memberMapper.existsById(type, keyword);
    }
}
