package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap05.service.PageMaker;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
@Builder
public class ReplyListResponseDTO {

    // 댓글 목록 페이징해야 하기 때문에 좀 더 여러개의 정보를 화면단으로 넘겨야 함
    // dto 새롭게 생성해서 댓글 수와 페이징 정보도 함꼐 넘기기
    private int count;
    private PageMaker pageInfo;
    private List<ReplyDetailResponseDTO> replies;

}
