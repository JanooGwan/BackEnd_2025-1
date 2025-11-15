package com.example.bcsd.repository;

import com.example.bcsd.model.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemberRepository {
    private final AtomicLong key = new AtomicLong(0);
    private final Map<Long, Member> members = new HashMap<>();

    public List<Member> findAll() {
        List<Member> result = new ArrayList<>();
        members.forEach((id, member) -> result.add(member));

        return result;
    }

    public Optional<Member> findById(Long id) {
        Member member = members.get(id);

        return Optional.ofNullable(member);
    }

    public Member save(Member member) {
        member.setId(key.incrementAndGet());
        members.put(key.get(), member);

        return member;
    }

    public Member update(Long id, Member member) {
        members.put(id, member);

        return member;
    }

    public void deleteById(Long id) {
        members.remove(id);
    }
}
