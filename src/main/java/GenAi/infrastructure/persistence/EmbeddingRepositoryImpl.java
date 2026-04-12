package GenAi.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class EmbeddingRepositoryImpl implements EmbeddingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DocumentEntity> findTopKWithVector(float[] embedding, int k) {
        String vectorAsString = "[" +
                IntStream.range(0, embedding.length)
                        .mapToObj(i -> String.valueOf(embedding[i]))
                        .collect(Collectors.joining(",")) + "]";

        // Utilisation d'une liste typée et vérification de la présence de données
        Query query = entityManager.createNativeQuery(
                "SELECT id, content, embedding FROM documents ORDER BY embedding <-> CAST(:vec AS vector) LIMIT :k",
                DocumentEntity.class);
        query.setParameter("vec", vectorAsString);
        query.setParameter("k", k);

        try {
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            return new ArrayList<>(); // Renvoie une liste vide propre
        }
    }
}