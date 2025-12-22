package com.example.bcsd.repository;

import com.example.bcsd.domain.beforejpa.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Article> articleRowMapper = (rs, rowNum) ->
            Article.builder()
                    .id(rs.getLong("id"))
                    .writerId(rs.getLong("writer_id"))
                    .boardId(rs.getLong("board_id"))
                    .title(rs.getString("title"))
                    .content(rs.getString("content"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .modifiedAt(
                            rs.getTimestamp("modified_at") != null
                                    ? rs.getTimestamp("modified_at").toLocalDateTime()
                                    : null
                    )
                    .build();

    public List<Article> findAll() {
        String sql = "SELECT * FROM article";
        return jdbcTemplate.query(sql, articleRowMapper);
    }

    public List<Article> findAllByBoardId(Long boardId) {
        String sql = "SELECT * FROM article WHERE board_id = ?";
        return jdbcTemplate.query(sql, articleRowMapper, boardId);
    }

    public Optional<Article> findById(Long id) {
        String sql = "SELECT * FROM article WHERE id = ?";
        List<Article> results = jdbcTemplate.query(sql, articleRowMapper, id);
        return results.stream().findFirst();
    }

    public Article save(Article article) {
        String sql = """
                INSERT INTO article (writer_id, board_id, title, content, created_at, modified_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                article.getWriterId(),
                article.getBoardId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getModifiedAt()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        article.setId(id);

        return article;
    }

    public Article update(Long id, Article article) {
        String sql = "UPDATE article SET title = ?, content = ? WHERE id = ?";

        jdbcTemplate.update(sql, article.getTitle(), article.getContent(), id);

        return article;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
