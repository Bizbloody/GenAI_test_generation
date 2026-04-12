package GenAi.config;

import dev.langchain4j.service.V;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import GenAi.infrastructure.persistence.EmbeddingRepository;
import GenAi.infrastructure.storage.PgEmbeddingStore;
import GenAi.core.api.Assistant;
import GenAi.core.agents.code_feedback.CodeFeedback;
import GenAi.core.agents.code_generator.CodeGenerator;
import GenAi.core.agents.documentation_parser.DocumentParser;
import GenAi.infrastructure.llm.FoundryLlmService;
import GenAi.infrastructure.llm.FoundryEmbeddingService;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;

@Configuration
public class AppConfig {

    @Bean
    public FoundryLlmService foundryLlmService(
            @Value("${AZURE_FOUNDRY_CHAT_ENDPOINT}") String endpoint,
            @Value("${AZURE_FOUNDRY_CHAT_KEY}") String apiKey,
            @Value("${AZURE_FOUNDRY_CHAT_MODEL}") String modelName
    ) {
        return new FoundryLlmService(endpoint, apiKey, modelName);
    }

    @Bean
    public FoundryEmbeddingService foundryEmbeddingService(
            @Value("${AZURE_FOUNDRY_EMBED_ENDPOINT}") String endpoint,
            @Value("${AZURE_FOUNDRY_EMBED_KEY}") String apiKey,
            @Value("${AZURE_FOUNDRY_EMBED_MODEL}") String modelName
    ) {
        return new FoundryEmbeddingService(endpoint, apiKey, modelName);
    }

    @Bean
    public EmbeddingStore pgEmbeddingStore(EmbeddingRepository repository) {
        return new PgEmbeddingStore(repository);
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore pgEmbeddingStore,
                                             FoundryEmbeddingService embeddingService,
                                             @Value("${RAG_TOP_K}") int topK) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(pgEmbeddingStore)
                .embeddingModel(embeddingService.getEmbeddingModel())
                .maxResults(topK)
                .build();
    }

    @Bean(name = "codeGeneratorAssistant")
    public Assistant codeGeneratorAssistant(CodeGenerator codeGenerator) {
        return codeGenerator.creationAgentCodeGenerator();
    }

    @Bean(name = "codeFeedbackAssistant")
    public Assistant codeFeedbackAssistant(CodeFeedback codeFeedback) {
        return codeFeedback.creationAgentCodeFeedback();
    }

    @Bean(name = "documentParserAssistant")
    public Assistant documentParserAssistant(DocumentParser documentParser) {
        return documentParser.creationAgentDocumentParser();
    }
}