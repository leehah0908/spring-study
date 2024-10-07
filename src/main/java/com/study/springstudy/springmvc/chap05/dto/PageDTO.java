package com.study.springstudy.springmvc.chap05.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PageDTO {

    private int pageNo;
    private int amount;

    public PageDTO() {
        // 게시판에 처음으로 들어왔을 때는 값들이 전달되지 않기 때문에 기본값 세팅
        // /board/list?pageNo=

        /*
         만약에 한페이지에 게시물을 10개씩 뿌린다고 가정하면
         1페이지 -> LIMIT 0, 10
         2페이지 -> LIMIT 10, 10
         3페이지 -> LIMIT 20, 10
         만약에 한페이지에 게시물을 6개씩 뿌린다고 가정하면
         1페이지 -> LIMIT 0, 6
         2페이지 -> LIMIT 6, 6
         3페이지 -> LIMIT 12, 6
         만약에 한페이지에 게시물을 N개씩 뿌린다고 가정하면
         1페이지 -> LIMIT 0, N
         2페이지 -> LIMIT 10, N
         3페이지 -> LIMIT 20, N
         M페이지 -> LIMIT (M - 1) * N, N
     */
        this.pageNo = 1;
        this.amount = 6;
    }

    public int getPageStart() {
        return (pageNo - 1) * amount;
    }
}
