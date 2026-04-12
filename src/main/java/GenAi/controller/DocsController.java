package GenAi.controller;

import GenAi.infrastructure.llm.FoundryEmbeddingService;
import GenAi.service.DocumentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.postgresql.util.PGobject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@RestController
@RequestMapping("/api/docs")
public class DocsController {

    private final FoundryEmbeddingService embeddingService;

    @PersistenceContext
    private EntityManager entityManager;

    private final DocumentService documentService;

    public DocsController(FoundryEmbeddingService embeddingService,
                          DocumentService documentService) {
        this.embeddingService = embeddingService;
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDoc(@RequestParam("file") MultipartFile file) {
        try {
            String content;

            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                PDFTextStripper stripper = new PDFTextStripper();
                content = stripper.getText(document);
            }

            List<String> chunks = split(content);

            documentService.saveChunks(chunks, embeddingService);

            return ResponseEntity.ok("Document vectorized and stored!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }

    private List<String> split(String content) {
        int chunkSize = 1000;
        int overlap = 200;

        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < content.length(); i += (chunkSize - overlap)) {
            int end = Math.min(i + chunkSize, content.length());
            String chunk = content.substring(i, end);

            // éviter les petits chunks inutiles
            if (chunk.trim().length() < 50) continue;

            chunks.add(chunk);
        }

        return chunks;
    }
}