package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Grade;
import com.study.springstudy.springmvc.chap03.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// controller처럼 component랑 기능은 똑같지만 구분을 위해서 명시적으로 Repository라고 함
@Repository
// final로 선언된 변수를 초기화시켜주는 생성자
@RequiredArgsConstructor
public class ScoreJdbcRepository implements ScoreRepository {

    // 여기서만 쓸거니꺼 내부 클래스로 선언
    class ScoreMapper implements RowMapper<Score> {

        // ResultSet: sql 실행(조회) 결과의 내용을 갖고 있는 객체
        // 타겟을 한 행씩 지목하면서 컬럼값을 가져올 수 있음
        // mapRow 메서드를 통해 jdbcTemplete한테 한 행의 컬럼값을 Score 객체로 포장하는 방법을 알려줘야 함
        @Override
        public Score mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Score(
                    rs.getInt("stu_num"),
                    rs.getString("stu_name"),
                    rs.getInt("kor"),
                    rs.getInt("eng"),
                    rs.getInt("math"),
                    rs.getInt("total"),
                    rs.getDouble("average"),
                    Grade.valueOf(rs.getString("grade"))
            );
        }
    }

    // spring-jdbc의 핵심 객체 JdbcTemplete의 의존성 주입 (생성자 주입)
    // 데이터베이스 접속 객체(Connection)를 바로 활용하는 것이 가능 -> 미리 세팅을 다 해놓음 (build.gradle에서)
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Score score) {
        String sql = "insert into score " +
                "(stu_name, kor, eng, math, total, average, grade) " +
                "values(?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                score.getStuName(), score.getKor(), score.getEng(), score.getMath(),
                score.getTotal(), score.getAverage(), score.getGrade().toString());
    }

    @Override
    public List<Score> findAll(String sort) {
        String sql = "select * from score";

        switch (sort) {
            case "num":
                sql += " order by stu_num";
                break;
            case "name":
                sql += " order by stu_name";
                break;
            case "avg":
                sql += " order by average desc";
                break;
        }

        // 여러 행이 조회될 때는 query()를 호출
        // sql, RowMapper 인터페이스를 구현한 객체를 전달
        // 조회된 내용을 어떤 방식으로 포장할지를 알려줘야 함 (테이블은 컬럼의 타입, 개수 등이 모두 다르기 때문)
        return jdbcTemplate.query(sql, new ScoreMapper());
    }

    @Override
    public Score findOne(int stuNum) {
        String sql = "select * from score where stu_num = ?";

        try {
            // queryForObject는 조회 결과가 없거나, 2개 이상일 경우 예외가 발생함
            return jdbcTemplate.queryForObject(sql, new ScoreMapper(), stuNum);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(int stuNum) {
        String sql = "delete from score where stu_num = ?";

        jdbcTemplate.update(sql, stuNum);
    }

    @Override
    public void update(Score score) {
        String sql = "update score " +
                "set kor = ?, eng = ?, math = ?, total = ?, average = ?, grade = ? " +
                "where stu_num = ?";

        jdbcTemplate.update(sql,
                score.getKor(), score.getEng(), score.getMath(), score.getTotal(),
                score.getAverage(), score.getGrade().toString(), score.getStuNum());
    }
}

