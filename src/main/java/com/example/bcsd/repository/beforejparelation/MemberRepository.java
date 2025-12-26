package com.example.bcsd.repository.beforejparelation;

import com.example.bcsd.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    List<Member> findAll();
    Optional<Member> findById(Long id);
    Member save(Member member);
    void deleteById(Long id);
    int countArticlesByMember(Long memberId);
    boolean existsByEmail(String email);
}
