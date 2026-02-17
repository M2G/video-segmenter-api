package com.example.video_segmenter_api.api.rest;

import com.example.video_segmenter_api.application.UpdateVideoMetadataUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/videos")
public class VideoMetadataController {

    private final UpdateVideoMetadataUseCase useCase;

    public VideoMetadataController(UpdateVideoMetadataUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/{id}/metadata")
    public void callback(
            @PathVariable UUID id,
            @RequestBody MetadataRequest request
    ) {
        useCase.execute(
                id,
                request.duration(),
                request.segmentsCount()
        );
    }

    record MetadataRequest(
            double duration,
            int segmentsCount
    ) {}
}
