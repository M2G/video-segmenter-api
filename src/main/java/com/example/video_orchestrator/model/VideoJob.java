package com.example.video_orchestrator.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_jobs")
public class VideoJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String filename;

    @Enumerated(EnumType.STRING)
    private VideoJobStatus status;

    private int retryCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public VideoJobStatus getStatus() {
        return status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ===== SETTERS =====

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setStatus(VideoJobStatus status) {
        this.status = status;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

