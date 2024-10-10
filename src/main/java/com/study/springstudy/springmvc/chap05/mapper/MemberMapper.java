package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    // 중복 확인 (아이디, 이메일)
    // type: 어떤 정보를 중복 확인할 것인지
    // keyword: 중복확인 할 값
    boolean existsById(String type, String keyword);

    // 회원 가입
    boolean save(Member member);

    // 회원 정보 개별 조회
    Member findOne(String account);

    // 회원 정보 수정
    void update();

    // 회원 탈퇴
    void delete(String account);
}
