package agents.documentation_parser;

import api.ApiConfiguration;
import api.Assistant;
import api.OpenAiService;
import rag.Rag;
import api.AzureOpenAiService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.rag.content.retriever.ContentRetriever;

public class DocumentParser {
    private final ApiConfiguration apiConfig;

    public DocumentParser(ApiConfiguration apiConfig) {
        this.apiConfig = apiConfig;
    }

    public Assistant creationAgentDocumentParser() {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        /*AzureOpenAiService azureService = new AzureOpenAiService(apiConfig);*/
        OpenAiService openAiService = new OpenAiService(apiConfig);
        /*Rag rag = new Rag();*/
        ContentRetriever contentRetriever = Rag.getInstance().getContentRetriever();

        return AiServices.builder(Assistant.class)
                .chatModel(/*azureService.getChatModel()*/openAiService.getChatModel())
                .chatMemory(chatMemory)
                /*.contentRetriever(rag.getEmbeddingStoreContentRetriever())*/
                .contentRetriever(contentRetriever)  // ContentRetriever est l'interface correcte
                .systemMessageProvider(chatMemoryId -> "As a documentation and code parser, analyze the following components to prepare for test generation:\n" +
                        "\n" +
                        "        1. FRAMEWORK DOCUMENTATION ANALYSIS:\n" +
                        "        %s\n" +
                        "\n" +
                        "        2. EXAMPLE CODE ANALYSIS:\n" +
                        "        %s\n" +
                        "\n" +
                        "        3. EXISTING CODE BASE ANALYSIS:\n" +
                        "        %s\n" +
                        "\n" +
                        "        Please provide a structured analysis with:\n" +
                        "\n" +
                        "        1. FRAMEWORK COMPONENTS:\n" +
                        "        - Identify key classes, methods, and interfaces from the documentation\n" +
                        "        - List important annotations and their purposes\n" +
                        "        - Extract configuration requirements\n" +
                        "        - Note any specific patterns or best practices mentioned\n" +
                        "\n" +
                        "        2. EXAMPLE PATTERNS:\n" +
                        "        - Extract test patterns from example code\n" +
                        "        - Identify common testing scenarios\n" +
                        "        - Note any specific assertions or validations used\n" +
                        "        - List dependencies and test configurations used\n" +
                        "\n" +
                        "        3. CODE BASE SPECIFICS:\n" +
                        "        - List classes and methods that need testing\n" +
                        "        - Identify dependencies and external services used\n" +
                        "        - Note any existing patterns or architectural decisions\n" +
                        "        - Highlight critical paths and edge cases\n" +
                        "\n" +
                        "        4. INTEGRATION POINTS:\n" +
                        "        - Identify points where the framework interacts with the existing code\n" +
                        "        - List required mocks or test doubles\n" +
                        "        - Note any specific configuration needed for testing\n" +
                        "\n" +
                        "        5. TESTING REQUIREMENTS:\n" +
                        "        - Suggest test categories (unit, integration, etc.)\n" +
                        "        - List required test dependencies\n" +
                        "        - Identify potential test data requirements\n" +
                        "        - Note any special testing considerations\n" +
                        "\n" +
                        "        Format the response as a structured JSON object for easy parsing by other agents.")
                .build();
    }
}
