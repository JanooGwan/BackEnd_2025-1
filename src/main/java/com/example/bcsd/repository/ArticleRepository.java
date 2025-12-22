package com.example.bcsd.repository;

import com.example.bcsd.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

    public List<Article> findAllByBoardId(Long boardId) {
        return em.createQuery("SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    public Article update(Long id, Article article) {
        em.createQuery("UPDATE Article a SET a.title = :title, a.content = :content, a.modifiedAt = :modifiedAt WHERE a.id = :id")
                .setParameter("title", article.getTitle())
                .setParameter("content", article.getContent())
                .setParameter("modifiedAt", article.getModifiedAt())
                .setParameter("id", id)
                .executeUpdate();
        return article;
    }

    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }
}
