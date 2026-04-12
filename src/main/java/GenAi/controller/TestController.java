package GenAi.controller;

import GenAi.dto.TestResponse;
import GenAi.service.TestGenerationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestGenerationService service;

    public TestController(TestGenerationService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<TestResponse> generateTests(
            @RequestParam("file") MultipartFile file) {

        String result = service.generateTests(file);

        return ResponseEntity.ok(new TestResponse(result));
    }
}