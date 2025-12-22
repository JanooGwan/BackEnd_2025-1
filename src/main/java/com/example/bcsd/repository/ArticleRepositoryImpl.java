package com.example.bcsd.repository;

import com.example.bcsd.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

    @Override
    public List<Article> findAllByBoardId(Long boardId) {
        return em.createQuery("SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }
}
