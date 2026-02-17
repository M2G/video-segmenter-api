package com.example.video_segmenter_api.infrastructure.orchestrator;

import org.springframework.stereotype.Component;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class VideoOrchestratorClient {

    private final RestClient restClient;

    public VideoOrchestratorClient(
            @Value("${video-orchestrator.base-url}") String baseUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public void send(UUID videoId, String filePath) {
        restClient.post()
                .uri("/videos")
                .body(new OrchestratorRequest(
                        videoId.toString(),
                        filePath
                ))
                .retrieve()
                .toBodilessEntity();
    }

    record OrchestratorRequest(String videoId, String filePath) {}
}
