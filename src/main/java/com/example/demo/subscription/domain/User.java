package com.example.demo.subscription.domain;

import com.example.demo.shared.domain.Identifier;

public class User {

    private final Identifier id;

    public User(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }
}

