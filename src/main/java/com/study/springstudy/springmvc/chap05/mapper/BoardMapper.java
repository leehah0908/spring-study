package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.dto.PageDTO;
import com.study.springstudy.springmvc.chap05.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 목록 조회
    List<Board> findAll(PageDTO pageDTO);

    // 상세 조회
    Board findOne(int boardNo);

    // 게시물 등록
    void save(Board board);

    // 게시물 삭제
    void delete(int boardNo);

    // 조회수 처리
    void updateViewCount(int boardNo);

    // 전체 게시글 갯수 조회
    int getCount();
}
