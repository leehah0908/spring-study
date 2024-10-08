package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.dto.request.SearchDTO;
import com.study.springstudy.springmvc.chap05.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
// 테스트가 끝나면 테스트에서 사용됐던 데이터는 롤백되서 기본의 db 데이터로 돌아감
@Transactional
@Rollback
class BoardMapperTest {

    @Autowired
    BoardMapper mapper;

    // 테스트 실행 전에 실행할 코드
//    @BeforeEach
    @Test
    void bulkInsert() {
        for (int i = 1; i <= 300; i++) {
            Board b = new Board();
            b.setTitle("테스트 제목" + i);
            b.setWriter("글쓴이" + i);
            b.setContent("글내용" + i);

            mapper.save(b);
        }
    }

    @Test
    @DisplayName("게시물 조회 테스트(페이징 조회)")
    void findAllTest() {
        // given
        SearchDTO pageDTO = new SearchDTO(); // pageNo: 1, amount: 6
        pageDTO.setPageNo(15);

        // when
        List<Board> boardList = mapper.findAll(pageDTO);

        // then
        boardList.forEach(System.out::println);
        assertEquals(6, boardList.size());
    }

    @Test
    // (auto_increment를 사용하면 bulkInsert에서 사용한 board_no 이후부터 생성하기 때문)
    @DisplayName("게시물 상세 조회 테스트 -> 5번 글을 상세 조회했을 때 작성자는 글쓴이5")
    void findOneTest() {
        // given
        int boardNo = 5;

        // when
        Board board = mapper.findOne(boardNo);

        // then
        assertEquals("글쓴이5", board.getWriter());
    }

    @Test
    @DisplayName("게시물 조회수 업데이트 테스트 -> 5번 글의 조회수를 3번 증가시켰을 때 조회수는 3")
    void updateViewTest() {
        // given
        int boardNo = 5;

        // when
        mapper.updateViewCount(boardNo);
        mapper.updateViewCount(boardNo);
        mapper.updateViewCount(boardNo);

        // then
        assertEquals(3, mapper.findOne(5).getViewCount());
    }

    @Test
    @DisplayName("게시물 삭제 테스트 -> 5번 글을 삭제한 후 조회하면 조회되지 않아야 함")
    void deleteTest() {
        // given
        int boardNo = 5;

        // when
        mapper.delete(boardNo);

        // then
        assertNull(mapper.findOne(boardNo));
    }


}