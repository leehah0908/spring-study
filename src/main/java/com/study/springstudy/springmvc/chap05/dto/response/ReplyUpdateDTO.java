package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap05.entity.Reply;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReplyUpdateDTO {

    private int rno;

    @NotBlank
    private String text;

    // dto -> entity
    public Reply toEntity() {
        return Reply.builder()
                .replyText(text)
                .replyNo(rno)
                .build();
    }
}
