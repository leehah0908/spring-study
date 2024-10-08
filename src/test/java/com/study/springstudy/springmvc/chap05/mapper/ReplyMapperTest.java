package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.entity.Board;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ReplyMapperTest {

    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    BoardMapper boardMapper;

    @Test
    @DisplayName("게시물을 100개 등록하고, 랜덤으로 1000개의 댓글을 게시글에 등록한다.")
    void bulkInsertTest() {
        // when
        for (int i = 1; i <= 100; i++) {
            Board b = Board.builder()
                    .title("게시물 게목 " + i)
                    .content("게시물 내용 " + i)
                    .writer("게시물 작성자 " + i)
                    .build();
            boardMapper.save(b);
        }

        for (int i = 1; i <= 1000; i++) {
            Reply reply = Reply.builder()
                    .replyText("댓글 내용 " + i)
                    .replyWriter("댓글 작성자 " + i)
                    .boardNo((int) (Math.random() * 100 + 1))
                    .build();
            replyMapper.save(reply);
        }
    }

    @Test
    @DisplayName("83번 게시물의 댓글 목록을 조회했을 때 갯수는 N이어야 함")
    void findAllTest() {
        // given
        int boredNo = 83;

        // when
        List<Reply> allList = replyMapper.findAll(boredNo);
//        allList.forEach(System.out::println);

        // then
        assertEquals(allList.size(), replyMapper.count(boredNo));
    }

    @Test
    @DisplayName("83번 게시물의 댓글 중 N번 댓글을 삭제하면, N번 댓글은 조회되지 않아야 함" +
                 "83번 게시물의 댓글 갯수는 1개 줄어들어야 함")
    void deleteTest() {
        // given
        int boardNo = 83;
        int replyNo = 188;
        int oldCount = replyMapper.count(boardNo);

        // when
        replyMapper.delete(replyNo);
        Reply one = replyMapper.findOne(replyNo);

        // then
        assertNull(one);
        assertNotEquals(replyMapper.count(boardNo), oldCount);

    }

    @Test
    @DisplayName("883번 댓글의 내용을 수정하면 다시 조회했을 때 수정된 내용이 조회되어야 함")
    void modifyTest() {
        // given
        int replyNo = 883;

        Reply testReply = Reply.builder().boardNo(replyNo).replyText("수정된 내용").build();
        String oldText = replyMapper.findOne(883).getReplyText();

        // when
        replyMapper.modify(testReply);
        String newText = testReply.getReplyText();

        // then
        assertNotEquals(oldText, newText);
    }
}