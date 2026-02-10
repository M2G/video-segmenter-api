package com.example.video_segmenter_api.infrastructure.orchestrator.persistence;

import com.example.video_segmenter_api.infrastructure.orchestrator.persistence.sqlc.Queries;
import com.example.video_segmenter_api.repository.VideoRepository;
import com.example.video_segmenter_api.domain.VideoStatus;
import com.example.video_segmenter_api.domain.VideoUpload;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SqlcVideoRepository implements VideoRepository {

    private final Queries queries;

    public SqlcVideoRepository(Queries queries) {
        this.queries = queries;
    }

    @Override
    public void save(VideoUpload video) {
        try {
            queries.insertVideo(
                    video.getId().toString(),
                    video.getFilePath(),
                    video.getStatus().name()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatus(UUID id, VideoStatus status) {
        try {
            queries.updateVideoStatus(
                    id.toString(),
                    status.name()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<VideoUpload> findById(UUID id) {
        try {
            var record = queries.findVideoById(id.toString());
            VideoUpload video = VideoUpload.create(record.filePath());
            video.markAsProcessed(
                    record.duration() == null ? 0 : record.duration(),
                    record.segmentsCount() == null ? 0 : record.segmentsCount()
            );
            return Optional.of(video);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateMetadata(
            UUID id,
            double duration,
            int segmentsCount,
            VideoStatus status
    ) {
        try {
            queries.updateVideoMetadata(
                    id.toString(),
                    duration,
                    segmentsCount,
                    status.name()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
