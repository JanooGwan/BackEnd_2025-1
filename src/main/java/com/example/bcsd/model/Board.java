package com.example.bcsd.model;

public class Board {
    private Long id;
    private String name;

    public Board(Long id, String name) {
        this.id = id;
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
