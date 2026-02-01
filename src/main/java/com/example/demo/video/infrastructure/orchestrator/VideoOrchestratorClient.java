package com.example.demo.video.infrastructure.orchestrator;

import com.example.demo.video.domain.VideoUpload;

public interface VideoOrchestratorClient {

    void send(VideoUpload video);
}


