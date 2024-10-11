package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.LoginRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입을 하면 비밀번호가 암호화되서 저장됨")
    void securityPasswordTest() {
        // given
        SignUpRequestDTO dto = SignUpRequestDTO.builder()
                .account("kim1234")
                .password("aaa1233")
                .email("kim3344@naver.com")
                .name("김철수")
                .build();

        // when
        boolean flag = memberService.join(dto);

        // then
        assertTrue(flag);
    }
    
    @Test
    @DisplayName("id가 존재하지 않는 경우 테스트")
    void noAccTest() {
        // given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .account("park1234")
                .password("aaa4321")
                .build();

        // when
        LoginResult result = memberService.authenticate(dto);
        
        // then
        assertEquals(result, LoginResult.NO_ACC);
    }

    @Test
    @DisplayName("pw가 틀린 경우 테스트")
    void noPwTest() {
        // given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .account("park1234")
                .password("aaa4321")
                .build();

        // when
        LoginResult result = memberService.authenticate(dto);

        // then
        assertEquals(result, LoginResult.NO_PW);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void successTest() {
        // given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .account("park1234")
                .password("aaa4321")
                .build();

        // when
        LoginResult result = memberService.authenticate(dto);

        // then
        assertEquals(result, LoginResult.SUCCESS);
    }
}