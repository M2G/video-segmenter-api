package com.example.demo.application;

import com.example.video.domain.VideoRepository;
import com.example.video.domain.VideoStatus;
import com.example.video.domain.VideoUpload;
import com.example.video.infrastructure.orchestrator.VideoOrchestratorClient;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UploadVideoUseCaseTest {

    @Test
    void should_upload_and_send_video() {

        FakeRepository repository = new FakeRepository();
        FakeOrchestrator orchestrator = new FakeOrchestrator();

        UploadVideoUseCase useCase =
                new UploadVideoUseCase(repository, orchestrator);

        useCase.execute("tmp/video/test.mp4");

        assertThat(repository.saved).isNotNull();
        assertThat(repository.saved.getStatus()).isEqualTo(VideoStatus.SENT);
        assertThat(orchestrator.called).isTrue();
    }

    static class FakeRepository implements VideoRepository {
        VideoUpload saved;

        @Override
        public void save(VideoUpload video) {
            this.saved = video;
        }

        @Override
        public void updateStatus(UUID id, VideoStatus status) {
            saved.markAsSent();
        }
    }

    static class FakeOrchestrator extends VideoOrchestratorClient {
        boolean called = false;

        @Override
        public void send(UUID videoId, String filePath) {
            called = true;
        }
    }
}
