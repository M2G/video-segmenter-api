package com.example.demo.video.repository;

import com.example.demo.video.domain.VideoUpload;

public interface VideoRepository {

    void save(VideoUpload video);

    VideoUpload findById(String videoId);
}
