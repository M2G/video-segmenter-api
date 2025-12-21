package com.example.video_orchestrator.services;

import com.example.video_orchestrator.model.VideoJob;
import com.example.video_orchestrator.model.VideoJobStatus;
import com.example.video_orchestrator.repository.VideoJobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrchestratorService {

    private final VideoJobRepository jobRepository;

    public OrchestratorService(VideoJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public VideoJob markProcessing(VideoJob job) {
        job.setStatus(VideoJobStatus.PROCESSING);
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    public VideoJob markDone(VideoJob job) {
        job.setStatus(VideoJobStatus.DONE);
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    public VideoJob markError(VideoJob job) {
        job.setRetryCount(job.getRetryCount() + 1);
        job.setStatus(VideoJobStatus.ERROR);
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }
}
