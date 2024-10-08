package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.PageDTO;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageMaker {

    // 시작 페이지, 끝 페이지, 마지막 페이지
    private int begin, end, finalPage;

    // 이전, 다음 버튼 활성화 여부
    private boolean prev, next;

    // 현재 사용자가 요청한 페이지 정보
    private PageDTO page;

    // 총 게시물의 갯수
    private int totalCount;

    // 화면에 보여질 버튼 갯수
    private static final int BUTTON_COUNT = 10;

    // 외부에서 전달받는 데이터이기 때문
    public PageMaker(PageDTO page, int totalCount) {
        this.page = page;
        this.totalCount = totalCount;

        makePageInfo();
    }

    // 페이징 알고리즘 전달 메서드
    private void makePageInfo() {
        // 끝 페이지 번호(end)
        this.end = (int) (Math.ceil((double) page.getPageNo() / BUTTON_COUNT) * BUTTON_COUNT);

        // 시작 페이지 번호(begin)
        this.begin = this.end - BUTTON_COUNT + 1;

        // 이전 버튼 활성화 (prev)
//        if (!(this.begin == 1)) {
//            this.prev = true;
//        }
        this.prev = begin > 1;

        // 마지막 페이지 (finalPage)
        this.finalPage = (int) Math.ceil((double) this.totalCount / page.getAmount());

        // 다음 버튼 활성화
        if (this.finalPage < this.end) {
            this.end = this.finalPage;
        }
        this.next = this.end < this.finalPage;


    }
}
