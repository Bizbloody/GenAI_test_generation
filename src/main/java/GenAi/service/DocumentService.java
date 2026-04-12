package GenAi.service;

import GenAi.infrastructure.llm.FoundryEmbeddingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    @PersistenceContext
    private EntityManager entityManager;

    private PGobject toPgVector(float[] embedding) {
        try {
            PGobject pgVector = new PGobject();
            pgVector.setType("vector");

            String vectorString = java.util.stream.IntStream.range(0, embedding.length)
                    .mapToObj(i -> Float.toString(embedding[i]))
                    .collect(java.util.stream.Collectors.joining(","));

            pgVector.setValue("[" + vectorString + "]");

            return pgVector;

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert embedding to pgvector", e);
        }
    }

    private String cleanText(String text) {
        return text
                .replace("\u0000", "")
                .replaceAll("\\p{C}", ""); // supprime NULL bytes
    }

    @Transactional
    public void saveChunks(List<String> chunks, FoundryEmbeddingService embeddingService) {

        for (String chunk : chunks) {

            float[] embedding = embeddingService.getEmbeddingModel()
                    .embed(chunk)
                    .content()
                    .vector();

            PGobject pgVector = toPgVector(embedding);

            String cleanedChunk = cleanText(chunk);

            entityManager.createNativeQuery(
                            "INSERT INTO documents (content, embedding) VALUES (?, CAST(? AS vector))")
                    .setParameter(1, cleanedChunk)
                    .setParameter(2, pgVector.getValue()) // ⚠️ IMPORTANT
                    .executeUpdate();
        }
    }
}