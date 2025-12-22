package com.example.bcsd.repository;

import com.example.bcsd.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b", Board.class)
                .getResultList();
    }

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public Board update(Long id, Board board) {
        em.createQuery("UPDATE Board b SET b.name = :name WHERE b.id = :id")
                .setParameter("name", board.getName())
                .setParameter("id", id)
                .executeUpdate();
        return board;
    }

    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }

    public int countArticlesByBoard(Long boardId) {
        Long count = em.createQuery("SELECT COUNT(a) FROM Article a WHERE a.boardId = :boardId", Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
        return count.intValue();
    }
}
