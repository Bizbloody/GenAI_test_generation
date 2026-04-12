package GenAi.infrastructure.persistence;

import java.util.List;

public interface EmbeddingRepositoryCustom {
    List<DocumentEntity> findTopKWithVector(float[] embedding, int k);
}