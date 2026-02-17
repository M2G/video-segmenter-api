package com.example.video_segmenter_api.application;

import com.example.video_segmenter_api.repository.VideoRepository;
import com.example.video_segmenter_api.domain.VideoUpload;
import com.example.video_segmenter_api.infrastructure.orchestrator.VideoOrchestratorClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UploadVideoUseCase {

    private final VideoRepository repository;
    private final VideoOrchestratorClient orchestrator;

    public UploadVideoUseCase(
            VideoRepository repository,
            VideoOrchestratorClient orchestrator
    ) {
        this.repository = repository;
        this.orchestrator = orchestrator;
    }

    public void execute(String filePath) {

        // Création + persistence
        VideoUpload video = VideoUpload.create(filePath);
        video.markAsUploaded();

        save(video);

        // Appel externe (hors transaction)
        orchestrator.send(video.getId(), video.getFilePath());

        // Update status
        video.markAsSent();
        updateStatus(video);
    }

    @Transactional
    protected void save(VideoUpload video) {
        repository.save(video);
    }

    @Transactional
    protected void updateStatus(VideoUpload video) {
        repository.updateStatus(video.getId(), video.getStatus());
    }
}

