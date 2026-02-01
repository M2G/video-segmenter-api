package com.example.demo.shared.domain;

import java.util.UUID;

public record Identifier(String value) {

    public static Identifier newId() {
        return new Identifier(UUID.randomUUID().toString());
    }
}
