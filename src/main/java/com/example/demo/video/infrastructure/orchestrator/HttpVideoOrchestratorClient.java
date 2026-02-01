package com.example.demo.video.infrastructure.orchestrator;

import com.example.demo.video.domain.VideoUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HttpVideoOrchestratorClient implements VideoOrchestratorClient {

    private final RestClient restClient;

    public HttpVideoOrchestratorClient(
            @Value("${video-orchestrator.base-url}") String baseUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void send(VideoUpload video) {
        restClient.post()
                .uri("/videos")
                .body(new OrchestratorRequest(
                        video.getId().value(),
                        video.getFilePath()
                ))
                .retrieve()
                .toBodilessEntity();
    }

    record OrchestratorRequest(String videoId, String path) {}
}
