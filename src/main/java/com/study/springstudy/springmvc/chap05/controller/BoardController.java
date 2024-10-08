package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.dto.response.BoardDetailResponseDTO;
import com.study.springstudy.springmvc.chap05.dto.request.BoardWriteRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.request.SearchDTO;
import com.study.springstudy.springmvc.chap05.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
1. 목록 조회 요청(/board/list: GET)
- req data: x (페이지 번호)
- response: chap05/list.jsp
- res data: 글 목록 리스트 -> model에 담아서 리턴 (bList)
            제목은 5자를 초과하면 안됨.
            내용은 30자를 초과하면 안됨.
            날짜 패턴은 yyyy-MM-dd HH:mm
            글 번호, 조회수, 작성자는 있는 그대로 운반할 것.

2. 글쓰기 화면 요청(/board/write: GET)
- req data: x
- response: chap05/write.jsp
- res data: x

3. 글쓰기 등록 요청(/board/write: POST)
- req data: writer, title, content -> 문자열 타입 (BoardWriteRequestDTO)
            DTO를 board로 바꿔서 mapper에게 전달 -> Board의 생성자를 이용
- response: 글 목록 페이지 요청이 다시 들어오게끔 (redirect)
- res data: x

4. 글 삭제 요청(/board/delete: POST)
- req data: boardNo -> int
- response: 글 목록 페이지 요청이 다시 들어오게끔 (redirect)
- res data: x

5. 글 상세 보기 요청(/board/detail/글번호: GET)
- req data: boardNo -> int
- response: chap05/detail.jsp
- res data: model에 특정 게시글 정보 담아서 리턴
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 목록 조회 (페이징 + 검색)
    @GetMapping("/list")
    public String list(@ModelAttribute("s") SearchDTO searchDTO,
                       Model model) {
        Map<String, Object> map = boardService.getList(searchDTO);
        model.addAttribute("bList", map.get("bList"));
        model.addAttribute("maker", map.get("pm"));
        return "chap05/list";
    }

    // 글쓰기 화면
    @GetMapping("/write")
    public String write() {
        return "chap05/write";
    }

    // 글 등록
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO dto) {
        boardService.save(dto);
        return "redirect:/board/list";
    }

    // 글 상세 보기
    @GetMapping("/detail/{bno}")
    public String detail(@PathVariable int bno,
                         // 모델 객체에 직접 데이터를 담는 로직을 생략할 수 있음
                         @ModelAttribute("s") SearchDTO searchDTO,
                         Model model) {
        BoardDetailResponseDTO dto = boardService.detail(bno);
        model.addAttribute("b", dto);
//        model.addAttribute("s", searchDTO);
        return "chap05/detail";
    }

    // 글 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam int boardNo) {
        boardService.delete(boardNo);
        return "redirect:/board/list";
    }
}
