package com.example.bcsd.repository;

import com.example.bcsd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(a) FROM Article a WHERE a.writer.id = :memberId")
    int countArticlesByMember(@Param("memberId") Long memberId);

    Optional<Member> findByEmail(String email);
}
