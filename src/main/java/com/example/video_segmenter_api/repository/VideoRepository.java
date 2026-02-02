package com.example.video_segmenter_api.repository;

import com.example.video_segmenter_api.domain.VideoStatus;
import com.example.video_segmenter_api.domain.VideoUpload;

import java.util.UUID;

public interface VideoRepository {

    void save(VideoUpload video);

    void updateStatus(UUID id, VideoStatus status);
}
