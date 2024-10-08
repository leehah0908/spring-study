package com.study.springstudy.springmvc.chap05.dto.request;

import com.study.springstudy.springmvc.chap05.entity.Reply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ReplyPostRequestDTO {

    // NotNull: Null만 안됨 (공백은 가능)
    // NotBlank: 공백도 안되고, Null도 안됨

    @NotBlank
    @Size(min = 1, max = 300)
    private String text;

    @NotBlank
    @Size(min = 2, max = 8)
    private String author;

    @NotNull
    private Integer bno;

    // dto를 엔터티로 바꾸는 변환 메서드
    public Reply toEntity() {
        return Reply.builder()
                .replyText(text)
                .replyWriter(author)
                .boardNo(bno)
                .build();
    }

}
