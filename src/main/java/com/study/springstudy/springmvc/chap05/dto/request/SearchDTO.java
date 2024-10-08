package com.study.springstudy.springmvc.chap05.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO extends PageDTO{

    // 검색 조건과 검색어
    private String type, keyword;

    // 조회 기능에 검색 기능을 붙여야 하기 때문에 데이터가 없으면 빈 문자열이 기본값이 되어야 함
    public SearchDTO() {
        this.keyword = "";
        this.type = "";
    }


}
