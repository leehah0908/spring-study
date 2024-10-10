package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    @DisplayName("회원가입 테스트")
    void saveTest() {
        // given
        Member mem = Member.builder()
                .account("abs1234")
                .password("aaa1111")
                .name("홍길동")
                .email("abs1234@naver.com")
                .build();

        // when
        boolean flag = memberMapper.save(mem);

        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("계정명이 abs1234인 회원의 중복 결과는 true여야 함")
    void existsAccountTest() {
        // given
        String acc = "abs1234";

        // when
        boolean flag = memberMapper.existsById("account", acc);

        // then
        assertTrue(flag);
    }

    @Test
    @DisplayName("이메일이 abs1234@naver.com인 회원의 중복 결과는 true여야 함")
    void existsEmailTest() {
        // given
        String email = "abs1234@naver.com";

        // when
        boolean flag = memberMapper.existsById("email", email);

        // then
        assertTrue(flag);
    }

}