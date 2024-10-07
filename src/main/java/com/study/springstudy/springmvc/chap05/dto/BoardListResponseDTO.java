package com.study.springstudy.springmvc.chap05.dto;

import com.study.springstudy.springmvc.chap05.entity.Board;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
public class BoardListResponseDTO {

    private final int boardNo;
    private final String shortTitle; // 5자 자르기
    private final String shortContent; // 30자 자르기
    private final String regDate; // yyyy=MM=dd HH:mm
    private final int viewCount;
    private final String writer;

    public BoardListResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.shortTitle = makeShortTitle(board.getTitle());
        this.shortContent = makeShortContent(board.getContent());
        this.regDate = makePrettierDateString(board.getRegDate());
        this.viewCount = board.getViewCount();
        this.writer = board.getWriter();
    }

    // ============================== 메서드 구역 ==============================
    private String sliceString(String target, int i) {
        if (target.length() > i) {
            return target.substring(0, i) + "...";
        } else {
            return target;
        }
    }

    private String makeShortTitle(String title) {
        return sliceString(title, 5);
    }

    private String makeShortContent(String content) {
        return sliceString(content, 30);
    }

    public static String makePrettierDateString(LocalDateTime regDate) {
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm")
                .format(regDate);
    }
}
