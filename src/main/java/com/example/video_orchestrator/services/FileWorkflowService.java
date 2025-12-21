package com.example.video_orchestrator.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileWorkflowService {

    public Path moveToProcessing(Path file, Path processingDir) throws IOException {
        Path target = processingDir.resolve(file.getFileName());
        return Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
    }

    public void moveToDone(Path file, Path doneDir) throws IOException {
        Files.move(file, doneDir.resolve(file.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);
    }

    public void moveToError(Path file, Path errorDir) throws IOException {
        Files.move(file, errorDir.resolve(file.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
