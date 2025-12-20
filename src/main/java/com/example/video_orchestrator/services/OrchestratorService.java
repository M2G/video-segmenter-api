package com.example.video_orchestrator.services;

import com.example.video_orchestrator.model.VideoJob;
import com.example.video_orchestrator.model.VideoJobStatus;
import com.example.video_orchestrator.repository.VideoJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class OrchestratorService {

    private static final int MAX_RETRY = 3;

    @Value("${video.done-dir}")
    private Path doneDir;

    @Value("${video.error-dir}")
    private Path errorDir;

    @Value("${video.watch-dir}")
    private Path watchDir;

    @Autowired
    private SegmenterService segmenter;

    @Autowired
    private VideoJobRepository repo;

    @Async("videoExecutor")
    public void process(VideoJob job, Path processingFile) {

        job.setStatus(VideoJobStatus.PROCESSING);
        job.setUpdatedAt(LocalDateTime.now());
        repo.save(job);

        boolean success = segmenter.segment(processingFile);

        if (success) {
            move(processingFile, doneDir);
            job.setStatus(VideoJobStatus.DONE);
        } else {
            job.setRetryCount(job.getRetryCount() + 1);

            if (job.getRetryCount() >= MAX_RETRY) {
                move(processingFile, errorDir);
                job.setStatus(VideoJobStatus.ERROR);
            } else {
                move(processingFile, watchDir);
                job.setStatus(VideoJobStatus.PENDING);
            }
        }

        job.setUpdatedAt(LocalDateTime.now());
        repo.save(job);
    }

    private void move(Path src, Path dstDir) {
        try {
            Files.move(
                    src,
                    dstDir.resolve(src.getFileName()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException ignored) {}
    }
}