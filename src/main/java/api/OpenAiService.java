package api;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatRequestParameters;


public class OpenAiService {
    private final ApiConfiguration config;
    private ChatModel chatModel;

    public OpenAiService(ApiConfiguration config) {
        this.config = config;
        initialize();
    }
    private void initialize() {

        chatModel = OpenAiChatModel.builder()
                .baseUrl(config.getBaseUrl())
                .apiKey(config.getApiKey())
                .modelName(config.getModelName())
                .temperature(0.0)
                .build();
    }

    public ChatModel getChatModel() {
        return chatModel;
    }
}
