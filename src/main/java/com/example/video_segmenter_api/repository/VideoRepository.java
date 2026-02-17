package com.example.video_segmenter_api.repository;

import com.example.video_segmenter_api.domain.VideoStatus;
import com.example.video_segmenter_api.domain.VideoUpload;

import java.util.UUID;

import java.util.Optional;

public interface VideoRepository {

    void save(VideoUpload video);

    void updateStatus(UUID id, VideoStatus status);

    Optional<VideoUpload> findById(UUID id);

    void updateMetadata(
            UUID id,
            double duration,
            int segmentsCount,
            VideoStatus status
    );
}
