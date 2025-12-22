package com.example.bcsd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Board(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
