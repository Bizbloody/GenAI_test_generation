package GenAi.service;

import GenAi.core.orchestrator.AgentOrchestrator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TestGenerationService {

    private final AgentOrchestrator orchestrator;

    public TestGenerationService(AgentOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    public String generateTests(MultipartFile file) {

        validate(file);

        String code = read(file);

        return orchestrator.execute(code);
    }

    private void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
    }

    private String read(MultipartFile file) {
        try {
            return new String(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("File read error", e);
        }
    }
}