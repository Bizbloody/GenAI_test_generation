package GenAi.infrastructure.storage;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import GenAi.infrastructure.persistence.EmbeddingRepository;
import GenAi.infrastructure.persistence.DocumentEntity;
import dev.langchain4j.store.embedding.EmbeddingStore;

import java.util.List;
import java.util.stream.Collectors;

public class PgEmbeddingStore implements EmbeddingStore<TextSegment> {

    private final EmbeddingRepository repository;

    public PgEmbeddingStore(EmbeddingRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmbeddingSearchResult<TextSegment> search(EmbeddingSearchRequest request) {
        // CORRECTION 1 : La méthode pour récupérer le vecteur est .queryEmbedding().vector()
        float[] queryVector = request.queryEmbedding().vector();

        // CORRECTION 2 : La méthode est .maxResults() et non .topK()
        int topK = request.maxResults();

        List<DocumentEntity> docs = repository.findTopKWithVector(queryVector, topK);

        // CORRECTION 3 : LangChain4j utilise EmbeddingMatch, pas Result
        List<EmbeddingMatch<TextSegment>> matches = docs.stream()
                .map(doc -> new EmbeddingMatch<>(
                        1.0, // Score (double)
                        doc.getId().toString(), // ID
                        null, // Embedding (facultatif ici)
                        TextSegment.from(doc.getContent()) // Contenu
                ))
                .collect(Collectors.toList());

        // CORRECTION 4 : Utilisation du constructeur simple
        return new EmbeddingSearchResult<>(matches);
    }

    @Override
    public String add(dev.langchain4j.data.embedding.Embedding embedding) { return null; }

    @Override
    public void add(String s, dev.langchain4j.data.embedding.Embedding embedding) {}

    @Override
    public String add(dev.langchain4j.data.embedding.Embedding embedding, TextSegment textSegment) { return null; }

    @Override
    public List<String> addAll(List<dev.langchain4j.data.embedding.Embedding> list) { return null; }

    @Override
    public List<String> addAll(List<dev.langchain4j.data.embedding.Embedding> list, List<TextSegment> list1) { return null; }
}