package com.example.demo.api.rest;

import com.example.demo.shared.domain.Identifier;
import com.example.demo.video.application.UploadVideoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final UploadVideoUseCase useCase;

    public VideoController(UploadVideoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<String> upload(
            @RequestParam String userId,
            @RequestParam MultipartFile file
    ) {

        String path = "tmp/video/" + file.getOriginalFilename();
        // stockage physique ailleurs (simplifié)

        Identifier videoId = useCase.execute(userId, path);
        return ResponseEntity.ok(videoId.value());
    }
}

