package api;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AzureOpenAiService {
    private static final Logger logger = LoggerFactory.getLogger(AzureOpenAiService.class);
    private final ApiConfiguration config;
    private ChatModel chatModel;

    public AzureOpenAiService(ApiConfiguration config) {
        this.config = config;
        initialize();
    }

    private void initialize() {
        /*try {
            logger.info("Initializing Azure OpenAI client");

            if (config == null) {
                throw new IllegalStateException("API configuration is missing");
            }

            chatModel = AzureOpenAiChatModel.builder()
                    .endpoint(config.getEndpoint())
                    .apiKey(config.getApiKey())
                    .deploymentName(config.getDeploymentId())
                    .temperature(0.1)
                    .maxTokens(4000)
                    .build();

            logger.info("Azure OpenAI client initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Azure OpenAI client: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Azure OpenAI service", e);
        }
    }

    /*public ChatModel getChatModel() {
        return chatModel;*/
    }


}