package GenAi.infrastructure.llm;

import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.data.segment.TextSegment;

import java.time.Duration;
import java.util.List;

public class FoundryEmbeddingService {

    private final OpenAiEmbeddingModel embeddingModel;

    public FoundryEmbeddingService(String endpoint, String apiKey, String modelName) {
        System.out.println("Embedding endpoint: " + endpoint);
        System.out.println("Embedding model: " + modelName);
        this.embeddingModel = OpenAiEmbeddingModel.builder()
                .baseUrl(endpoint)
                .apiKey(apiKey)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(180))
                .build();
    }

    public OpenAiEmbeddingModel getEmbeddingModel() {
        return embeddingModel;
    }


    public float[] embed(String text) {
        // LangChain4j renvoie une Response contenant un objet Embedding
        Response<Embedding> response = embeddingModel.embed(text);

        // .vector() renvoie directement un float[] dans les versions récentes
        return response.content().vector();
    }

    public List<float[]> embed(List<String> texts) {
        // 1. Convertir List<String> en List<TextSegment>
        List<TextSegment> segments = texts.stream()
                .map(TextSegment::from)
                .toList();

        // 2. Envoyer la liste de segments au modèle
        Response<List<Embedding>> responses = embeddingModel.embedAll(segments);

        // 3. Extraire les vecteurs (float[])
        return responses.content().stream()
                .map(Embedding::vector)
                .toList();
    }
}