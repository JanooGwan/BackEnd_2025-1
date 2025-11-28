package com.example.bcsd.repository;

import com.example.bcsd.model.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Board> boardRowMapper = (rs, rowNum) ->
            Board.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();

    public List<Board> findAll() {
        String sql = "SELECT * FROM board";
        return jdbcTemplate.query(sql, boardRowMapper);
    }

    public Optional<Board> findById(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";
        List<Board> results = jdbcTemplate.query(sql, boardRowMapper, id);
        return results.stream().findFirst();
    }

    public Board save(Board board) {
        String sql = "INSERT INTO board (name) VALUES (?)";

        jdbcTemplate.update(sql, board.getName());

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        board.setId(id);
        return board;
    }

    public Board update(Long id, Board board) {
        String sql = "UPDATE board SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, board.getName(), id);
        return board;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int countArticlesByBoard(Long boardId) {
        String sql = "SELECT COUNT(*) FROM article WHERE board_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, boardId);
    }
}
