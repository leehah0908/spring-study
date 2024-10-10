package com.study.springstudy.springmvc.chap05.controller;

import com.study.springstudy.springmvc.chap05.dto.response.ReplyUpdateDTO;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyPostRequestDTO;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyListResponseDTO;
import com.study.springstudy.springmvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        replyService.register(dto);
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/{boardNo}/page/{pageNo}")
    public ResponseEntity<?> list(@PathVariable int boardNo,
                                  @PathVariable int pageNo) {
        ReplyListResponseDTO replies = replyService.getList(boardNo, pageNo);

        return ResponseEntity.ok().body(replies);
    }

    @PatchMapping
    public ResponseEntity<?> update(@Validated @RequestBody ReplyUpdateDTO dto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.toString());
        }

        replyService.update(dto);
        return ResponseEntity.ok().body("Success");
    }

    @DeleteMapping("/{replyNo}")
    public ResponseEntity<?> delete(@PathVariable int replyNo) {
        try {
            replyService.delete(replyNo);
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
