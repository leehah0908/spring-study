package com.study.springstudy.springmvc.chap05.entity;

import com.study.springstudy.springmvc.chap05.dto.BoardWriteRequestDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    /*
    create table tbl_board (
        board_no int primary key auto_increment,
        title varchar(100) not null,
        content varchar(2000),
        view_count int default 0,
        reg_date datetime default current_timestamp,
        writer varchar(50) not null
    );
     */

    private int boardNo;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime regDate;
    private String writer;

    public Board(BoardWriteRequestDTO br) {
        this.title = br.getTitle();
        this.content = br.getContent();
        this.writer = br.getWriter();
    }
}
