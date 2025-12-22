package com.example.bcsd.repository;

import com.example.bcsd.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member update(Long id, Member member) {
        em.createQuery("UPDATE Member m SET m.name = :name, m.email = :email, m.password = :password WHERE m.id = :id")
                .setParameter("name", member.getName())
                .setParameter("email", member.getEmail())
                .setParameter("password", member.getPassword())
                .setParameter("id", id)
                .executeUpdate();
        return member;
    }

    public void deleteById(Long id) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            em.remove(member);
        }
    }

    public int countArticlesByMember(Long memberId) {
        Long count = em.createQuery("SELECT COUNT(a) FROM Article a WHERE a.writerId = :writerId", Long.class)
                .setParameter("writerId", memberId)
                .getSingleResult();
        return count.intValue();
    }

    public boolean existsByEmail(String email) {
        Long count = em.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}
