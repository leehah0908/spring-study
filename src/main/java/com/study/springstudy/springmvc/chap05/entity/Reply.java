package com.study.springstudy.springmvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter // 엔터티에서 setter는 꼭 필요한 필드에'만' 직접 추가함
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Reply {

    /*
    create table tbl_reply (
        reply_no int auto_increment,
        reply_text varchar(1000) not null,
        reply_writer varchar(100) not null,
        reply_date datetime default current_timestamp,
        board_no int,

        constraint pk_reply primary key(reply_no),
        constraint fk_reply foreign key(board_no)
        references tbl_board(board_no) on delete cascade

    );

    ALTER TABLE tbl_reply
    ADD account VARCHAR(50);

    ALTER TABLE tbl_reply
    ADD CONSTRAINT fk_reply_account
    FOREIGN KEY (account)
    REFERENCES tbl_member (account)
    ON DELETE CASCADE;
     */

    private int replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private int boardNo;
    private String account;

}
