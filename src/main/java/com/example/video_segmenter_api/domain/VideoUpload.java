package com.example.video_segmenter_api.domain;

import java.util.UUID;

public class VideoUpload {

    private final UUID id;
    private final String filePath;
    private VideoStatus status;

    private VideoUpload(UUID id, String filePath) {
        this.id = id;
        this.filePath = filePath;
        this.status = VideoStatus.NEW;
    }

    public static VideoUpload create(String filePath) {
        return new VideoUpload(UUID.randomUUID(), filePath);
    }

    public void markAsUploaded() {
        this.status = VideoStatus.UPLOADED;
    }

    public void markAsSent() {
        this.status = VideoStatus.SENT;
    }

    public UUID getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public VideoStatus getStatus() {
        return status;
    }
}
