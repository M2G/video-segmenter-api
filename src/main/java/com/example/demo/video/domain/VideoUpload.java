package com.example.demo.video.domain;

import com.example.demo.shared.domain.Identifier;

public class VideoUpload {

    private final Identifier id;
    private final Identifier userId;
    private final String filePath;
    private VideoStatus status;
    private VideoMetadata metadata;

    public VideoUpload(Identifier id, Identifier userId, String filePath) {
        this.id = id;
        this.userId = userId;
        this.filePath = filePath;
        this.status = VideoStatus.NEW;
    }

    public void markAsUploaded() {
        this.status = VideoStatus.UPLOADED;
    }

    public void markAsSent() {
        this.status = VideoStatus.SENT_TO_ORCHESTRATOR;
    }

    public void updateMetadata(double duration, int segmentsCount) {
        this.metadata = new VideoMetadata(duration, segmentsCount);
        this.status = VideoStatus.PROCESSED;
    }

    // Getters
    public Identifier getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public VideoStatus getStatus() {
        return status;
    }

    public VideoMetadata getMetadata() {
        return metadata;
    }
}

