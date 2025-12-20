package com.example.video_orchestrator.repository;

import com.example.video_orchestrator.model.VideoJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoJobRepository extends JpaRepository<VideoJob, Long> {
    Optional<VideoJob> findByFilename(String filename);
}
