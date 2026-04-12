package GenAi.infrastructure.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface EmbeddingRepository extends Repository<DocumentEntity, Long>, EmbeddingRepositoryCustom {

    @Query(value = """
        SELECT * FROM documents
        ORDER BY embedding <-> :embedding
        LIMIT :k
    """, nativeQuery = true)
    List<DocumentEntity> findTopK(@Param("embedding") float[] embedding,
                                  @Param("k") int k);
}