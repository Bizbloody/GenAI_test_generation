package GenAi.infrastructure.llm;

import dev.langchain4j.model.openai.OpenAiChatModel;

import java.time.Duration;

public class FoundryLlmService {

    private OpenAiChatModel chatModel;

    public FoundryLlmService(String endpoint, String apiKey, String modelName) {

        System.out.println("Chat endpoint: " + endpoint);
        System.out.println("Chat model/deployment: " + modelName);


        this.chatModel = OpenAiChatModel.builder()
                .baseUrl(endpoint)
                .apiKey(apiKey)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(180))
                .build();
    }

    public OpenAiChatModel getChatModel() {
        return chatModel;
    }
}