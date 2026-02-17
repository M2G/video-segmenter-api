package com.example.video_segmenter_api.api.rest;

import com.example.video_segmenter_api.application.UploadVideoUseCase;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final UploadVideoUseCase useCase;

    public VideoController(UploadVideoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        Path target = Path.of("tmp/video", file.getOriginalFilename());
        Files.createDirectories(target.getParent());
        Files.write(target, file.getBytes());

        useCase.execute(target.toString());
    }
}
