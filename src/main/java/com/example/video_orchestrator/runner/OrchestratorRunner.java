package com.example.video_orchestrator.runner;

import com.example.video_orchestrator.model.VideoJob;
import com.example.video_orchestrator.model.VideoJobStatus;
import com.example.video_orchestrator.repository.VideoJobRepository;
import com.example.video_orchestrator.services.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Component
public class OrchestratorRunner implements CommandLineRunner
{
    @Value("${video.watch-dir}")
    private Path watchDir;

    @Value("${video.processing-dir}")
    private Path processingDir;

    @Autowired
    private VideoJobRepository repo;

    @Autowired
    private OrchestratorService orchestrator;

    @Override
    public void run(String... args) throws Exception {
        Files.list(watchDir)
                .filter(p -> p.toString().endsWith(".mp4"))
                .forEach(this::submitJob);
    }

    private void submitJob(Path file) {
        String name = file.getFileName().toString();

        VideoJob job = repo.findByFilename(name)
                .orElseGet(() -> repo.save(newJob(name)));

        if (job.getStatus() != VideoJobStatus.PENDING) return;

        try {
            Path processingFile = Files.move(
                    file,
                    processingDir.resolve(name),
                    StandardCopyOption.ATOMIC_MOVE
            );

            orchestrator.process(job, processingFile);

        } catch (IOException ignored) {}
    }

    private VideoJob newJob(String name) {
        VideoJob j = new VideoJob();
        j.setFilename(name);
        j.setStatus(VideoJobStatus.PENDING);
        j.setRetryCount(0);
        j.setCreatedAt(LocalDateTime.now());
        return j;
    }
}
