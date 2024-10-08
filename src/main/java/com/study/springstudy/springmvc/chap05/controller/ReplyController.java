package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.dto.request.ReplyPostRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyDetailResponseDTO;
import com.study.springstudy.springmvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody ReplyPostRequestDTO dto,
                                    BindingResult result) { // 검증 결과 메세지를 가진 객체
        if (result.hasErrors()) {
            // 입력값 검증에 걸리면 400번 status와 함께 메세지를 클라이언트로 전송
            return ResponseEntity.badRequest().body(result.toString());
        }

        System.out.println("정상 포스트");
        System.out.println(dto);
        replyService.register(dto);

        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<?> list(@PathVariable int boardNo) {
        List<ReplyDetailResponseDTO> detailList = replyService.getList(boardNo);
        return ResponseEntity.ok().body(detailList);
    }


}
