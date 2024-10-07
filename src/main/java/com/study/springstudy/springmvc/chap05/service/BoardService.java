package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.BoardDetailResponseDTO;
import com.study.springstudy.springmvc.chap05.dto.BoardListResponseDTO;
import com.study.springstudy.springmvc.chap05.dto.BoardWriteRequestDTO;
import com.study.springstudy.springmvc.chap05.entity.Board;
import com.study.springstudy.springmvc.chap05.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    // MyBatis가 xml을 클래스로 변환해서 bean 등록을 해두기 때문에 주입이 가능
    private final BoardMapper mapper;

    public List<BoardListResponseDTO> getList() {
        return mapper.findAll()
                .stream()
                .map(BoardListResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void save(BoardWriteRequestDTO dto) {
        mapper.save(new Board(dto)); // dto를 엔터티로 변환해서 mapper로 전달
    }

    public BoardDetailResponseDTO detail(int bno) {
        mapper.updateViewCount(bno);
        return new BoardDetailResponseDTO(mapper.findOne(bno));
    }

    public void delete(int boardNo) {
        mapper.delete(boardNo);
    }
}
