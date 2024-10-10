package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap05.entity.Board;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BoardDetailResponseDTO{
    private final int boardNo;
    private final String title;
    private final String content;
    private final String regDate;
    private final String writer;
    private final int viewCount;

    private int replyCount;

    public BoardDetailResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getWriter();
        this.regDate = BoardListResponseDTO.makePrettierDateString(board.getRegDate());
        this.viewCount = board.getViewCount();

    }
}
