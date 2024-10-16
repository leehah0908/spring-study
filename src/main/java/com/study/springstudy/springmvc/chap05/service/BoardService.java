package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.BoardWriteRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SearchDTO;
import com.study.springstudy.springmvc.chap05.dto.response.BoardDetailResponseDTO;
import com.study.springstudy.springmvc.chap05.dto.response.BoardListResponseDTO;
import com.study.springstudy.springmvc.chap05.entity.Board;
import com.study.springstudy.springmvc.chap05.mapper.BoardMapper;
import com.study.springstudy.springmvc.util.LoginUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    // MyBatis가 xml을 클래스로 변환해서 bean 등록을 해두기 때문에 주입이 가능
    private final BoardMapper mapper;

    public Map<String, Object> getList(SearchDTO searchDTO) {
        // 전체 게시글을 가지고 오는 것이 아니라 특정 페이지 부분만 가져와야 함
        List<BoardDetailResponseDTO> boardList = mapper.findAll(searchDTO);
        List<BoardListResponseDTO> dtoList = boardList
                .stream()
                .map(BoardListResponseDTO::new)
                .collect(Collectors.toList());

        PageMaker pageMaker = new PageMaker(searchDTO, mapper.getCount(searchDTO));

        Map<String, Object> result = new HashMap<>();
        result.put("bList", dtoList);
        result.put("pm", pageMaker);

        return result;
    }

    public void save(BoardWriteRequestDTO dto, HttpSession session) {
        // 작성자 전달 X -> session에서 account 정보 가져와서 대입
        Board board = new Board(dto);
        board.setWriter(LoginUtils.getCurrentLoginMember(session));

        mapper.save(board);
    }

    public BoardDetailResponseDTO detail(int bno) {
        mapper.updateViewCount(bno);
        return new BoardDetailResponseDTO(mapper.findOne(bno));
    }

    public void delete(int boardNo) {
        mapper.delete(boardNo);
    }


}
