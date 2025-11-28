package com.example.bcsd.repository;

import com.example.bcsd.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) ->
            Member.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build();

    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        List<Member> results = jdbcTemplate.query(sql, memberRowMapper, id);
        return results.stream().findFirst();
    }

    public Member save(Member member) {
        String sql = "INSERT INTO member (name, email, password) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                member.getName(),
                member.getEmail(),
                member.getPassword()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        member.setId(id);
        return member;
    }

    public Member update(Long id, Member member) {
        String sql = "UPDATE member SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                id
        );
        return member;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int countArticlesByMember(Long memberId) {
        String sql = "SELECT COUNT(*) FROM article WHERE writer_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, memberId);
    }
}
