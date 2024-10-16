package com.study.springstudy.springmvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ReplyDetailResponseDTO {

    private int rno;
    private String text;
    private String writer;
    private String account;
    private String profile;

    // 나중에 DTO가 JSON으로 변환될 때 원하는 Format 형식으로 자동 변환
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime regDate;

    public ReplyDetailResponseDTO(Reply reply) {
        this.rno = reply.getReplyNo();
        this.text = reply.getReplyText();
        this.writer = reply.getReplyWriter();
        this.regDate = reply.getReplyDate();
        this.account = reply.getAccount();
        this.profile = reply.getProfileImage();
    }
}
