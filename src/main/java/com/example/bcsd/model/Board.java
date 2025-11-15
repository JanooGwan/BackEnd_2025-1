package com.example.bcsd.model;

public class Board {
    private Long id;
    private String name;

    public Board(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void update(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
