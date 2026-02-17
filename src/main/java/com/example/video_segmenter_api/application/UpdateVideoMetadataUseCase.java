package com.example.video_segmenter_api.application;

import com.example.video_segmenter_api.repository.VideoRepository;
import com.example.video_segmenter_api.domain.VideoStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateVideoMetadataUseCase {

    private final VideoRepository repository;

    public UpdateVideoMetadataUseCase(VideoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(
            UUID videoId,
            double duration,
            int segmentsCount
    ) {
        repository.updateMetadata(
                videoId,
                duration,
                segmentsCount,
                VideoStatus.PROCESSED
        );
    }
}
