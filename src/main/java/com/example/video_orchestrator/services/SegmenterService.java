package com.example.video_orchestrator.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SegmenterService {

    @Value("${video.segmenter-bin}")
    private String segmenterBin;

    @Value("${video.hls-dir}")
    private String hlsDir;

    public boolean segment(Path inputVideo) {
        String baseName = stripExt(inputVideo.getFileName().toString());

        List<String> command = List.of(
                segmenterBin,
                inputVideo.toString(),
                hlsDir + "/" + baseName,
                hlsDir + "/" + baseName + "/index.m3u8",
                "segment",
                ".ts",
                "10",
                "0"
        );

        try {
            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();

            process.getInputStream()
                    .transferTo(System.out);

            return process.waitFor() == 0;

        } catch (Exception e) {
            return false;
        }
    }

    private String stripExt(String f) {
        int i = f.lastIndexOf('.');
        return i > 0 ? f.substring(0, i) : f;
    }
}

