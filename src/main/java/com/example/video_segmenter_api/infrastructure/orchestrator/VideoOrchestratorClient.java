package com.example.video_segmenter_api.infrastructure.orchestrator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VideoOrchestratorClient {

    public void send(UUID videoId, String filePath) {
        // HTTP call vers video_orchestrator
        // volontairement vide pour l’exemple
    }
}

