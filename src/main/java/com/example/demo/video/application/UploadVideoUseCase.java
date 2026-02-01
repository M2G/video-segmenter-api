package com.example.demo.video.application;

import com.example.demo.shared.domain.Identifier;
import com.example.demo.video.domain.VideoUpload;
import com.example.demo.video.infrastructure.orchestrator.VideoOrchestratorClient;
import com.example.demo.video.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UploadVideoUseCase {

    private final VideoRepository videoRepository;
    private final VideoOrchestratorClient orchestratorClient;

    public UploadVideoUseCase(
            VideoRepository videoRepository,
            VideoOrchestratorClient orchestratorClient
    ) {
        this.videoRepository = videoRepository;
        this.orchestratorClient = orchestratorClient;
    }

    @Transactional
    public Identifier execute(String userId, String filePath) {

        VideoUpload video = new VideoUpload(
                Identifier.newId(),
                new Identifier(userId),
                filePath
        );

        video.markAsUploaded();
        videoRepository.save(video);

        orchestratorClient.send(video);

        video.markAsSent();
        videoRepository.save(video);

        return video.getId();
    }
}
