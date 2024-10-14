package com.study.springstudy.springmvc.chap05.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/display")
@Slf4j
public class ImageController {

    // img 태그의 src 속성을 통래서 들어오는 요청 처리
    // 페이지가 랜더링될 때, img에 작성된 요청 url을 통해 비동기 방식의 요청이 들어옴
    @GetMapping("/Users/leehah/Playdata_backend/upload")
    public ResponseEntity<?> getImage(HttpServletRequest request) {
        String uri = request.getRequestURI();

        File file = new File(uri);
        ResponseEntity<byte[]> result = null;
        HttpHeaders headers = new HttpHeaders(); // 응답용 헤더 객체 설정

        try {
            // 매개값으로 전달받은 파일의 타입이 무엇인지 문자열로 변환
            // 전달하고자 하는 파일을 카피한 후 바이트 배열로 변환해서 전달
            headers.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return result;
    }
}
