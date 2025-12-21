package com.example.video_orchestrator.runner;

import com.example.video_orchestrator.model.VideoJob;
import com.example.video_orchestrator.model.VideoJobStatus;
import com.example.video_orchestrator.repository.VideoJobRepository;
import com.example.video_orchestrator.services.FileWorkflowService;
import com.example.video_orchestrator.services.OrchestratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.time.LocalDateTime;

@Component
public class OrchestratorRunner implements CommandLineRunner {

    @Value("${video.watch-dir}")
    private Path watchDir;

    @Value("${video.processing-dir}")
    private Path processingDir;

    @Value("${video.done-dir}")
    private Path doneDir;

    @Value("${video.error-dir}")
    private Path errorDir;

    private final VideoJobRepository jobRepository;
    private final FileWorkflowService fileService;
    private final OrchestratorService orchestratorService;

    public OrchestratorRunner(VideoJobRepository jobRepository,
                              FileWorkflowService fileService,
                              OrchestratorService orchestratorService) {
        this.jobRepository = jobRepository;
        this.fileService = fileService;
        this.orchestratorService = orchestratorService;
    }

    @Override
    public void run(String... args) {
        System.out.println(">>> OrchestratorRunner STARTED");
        System.out.println("watchDir = " + watchDir.toAbsolutePath());
        System.out.println("processingDir = " + processingDir.toAbsolutePath());
        try {
            Files.createDirectories(processingDir);
            Files.createDirectories(doneDir);
            Files.createDirectories(errorDir);

            Files.list(watchDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            VideoJob job = jobRepository
                                    .findByFilename(file.getFileName().toString())
                                    .orElseGet(() -> {
                                        VideoJob j = new VideoJob();
                                        j.setFilename(file.getFileName().toString());
                                        j.setStatus(VideoJobStatus.PENDING);
                                        j.setRetryCount(0);
                                        j.setCreatedAt(LocalDateTime.now());
                                        return jobRepository.save(j);
                                    });

                            if (job.getStatus() == VideoJobStatus.PENDING ||
                                    job.getStatus() == VideoJobStatus.ERROR) {

                                Path processingFile =
                                        fileService.moveToProcessing(file, processingDir);

                                orchestratorService.markProcessing(job);

                                // 👉 ici, un autre système segmente la vidéo

                                fileService.moveToDone(processingFile, doneDir);
                                orchestratorService.markDone(job);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
