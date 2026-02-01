package com.example.demo.video.persistence;

import com.example.demo.video.domain.VideoUpload;
import com.example.demo.video.repository.VideoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JpaVideoRepository implements VideoRepository {

    // ⚠️ Fake persistence (en attendant le mapping JPA)
    private final Map<String, VideoUpload> store = new ConcurrentHashMap<>();

    @Override
    public void save(VideoUpload video) {
        store.put(video.getId().value(), video);
    }

    @Override
    public VideoUpload findById(String videoId) {
        VideoUpload video = store.get(videoId);
        if (video == null) {
            throw new IllegalStateException("Video not found: " + videoId);
        }
        return video;
    }
}
