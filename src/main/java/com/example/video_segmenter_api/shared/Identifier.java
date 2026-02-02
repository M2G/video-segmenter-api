package com.example.video_segmenter_api.shared;

import java.util.UUID;

public record Identifier(String value) {

    public static Identifier newId() {
        return new Identifier(UUID.randomUUID().toString());
    }
}
