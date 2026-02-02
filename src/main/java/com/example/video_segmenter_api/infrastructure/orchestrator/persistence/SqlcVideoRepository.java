package com.example.video_segmenter_api.infrastructure.orchestrator.persistence;

import com.example.video_segmenter_api.infrastructure.orchestrator.persistence.sqlc.Queries;
import com.example.video_segmenter_api.repository.VideoRepository;
import com.example.video_segmenter_api.domain.VideoStatus;
import com.example.video_segmenter_api.domain.VideoUpload;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SqlcVideoRepository implements VideoRepository {

    private final Queries queries;

    public SqlcVideoRepository(Queries queries) {
        this.queries = queries;
    }

    @Override
    public void save(VideoUpload video) {
        queries.insertVideo(
                video.getId(),
                video.getFilePath(),
                video.getStatus().name()
        );
    }

    @Override
    public void updateStatus(UUID id, VideoStatus status) {
        queries.updateVideoStatus(id, status.name());
    }
}
