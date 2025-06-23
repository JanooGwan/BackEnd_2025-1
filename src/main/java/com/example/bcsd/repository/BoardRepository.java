package com.example.bcsd.repository;

import com.example.bcsd.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b.name FROM Board b WHERE b.id = :id")
    Optional<String> findNameById(Long id);
}
