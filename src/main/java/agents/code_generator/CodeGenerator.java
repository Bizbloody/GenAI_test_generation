package agents.code_generator;

import api.ApiConfiguration;
import api.Assistant;
import rag.Rag;
import api.AzureOpenAiService;
import api.OpenAiService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.rag.content.retriever.ContentRetriever;

public class CodeGenerator {
    private final ApiConfiguration apiConfig;

    public CodeGenerator(ApiConfiguration apiConfig) { this.apiConfig = apiConfig; }

    public Assistant creationAgentCodeGenerator() {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        /*AzureOpenAiService azureService = new AzureOpenAiService(apiConfig);*/
        OpenAiService openAiService = new OpenAiService(apiConfig);
        /*Rag rag = new Rag();*/
        // Utiliser l'instance unique de RAG
        ContentRetriever contentRetriever = Rag.getInstance().getContentRetriever();


        return AiServices.builder(Assistant.class)
                .chatModel(/*azureService.getChatModel()*/openAiService.getChatModel())
                .chatMemory(chatMemory)
                /*.contentRetriever(rag.getEmbeddingStoreContentRetriever())*/
                .contentRetriever(contentRetriever)  // ContentRetriever est l'interface correcte
                .systemMessageProvider(chatMemoryId -> "As a test code generator, use the following analysis and requirements to generate comprehensive test code.\n" +
                        "\n" +
                        "            PARSED DOCUMENTATION AND CODE ANALYSIS:\n" +
                        "            %s\n" +
                        "\n" +
                        "            REQUIREMENT:\n" +
                        "            %s\n" +
                        "\n" +
                        "            Please generate test code following these guidelines:\n" +
                        "\n" +
                        "            1. TEST STRUCTURE:\n" +
                        "            - Create a proper test class with appropriate annotations\n" +
                        "            - Include necessary imports\n" +
                        "            - Add clear test method names that describe the test purpose\n" +
                        "            - Structure tests following AAA pattern (Arrange, Act, Assert)\n" +
                        "\n" +
                        "            2. TESTING COVERAGE:\n" +
                        "            - Include both positive and negative test scenarios\n" +
                        "            - Cover edge cases identified in the analysis\n" +
                        "            - Test all critical paths mentioned\n" +
                        "            - Include integration points testing where necessary\n" +
                        "\n" +
                        "            3. BEST PRACTICES:\n" +
                        "            - Follow testing best practices from the framework documentation\n" +
                        "            - Use appropriate assertions and matchers\n" +
                        "            - Include proper test documentation/comments\n" +
                        "            - Implement proper setup and teardown if needed\n" +
                        "\n" +
                        "            4. MOCKING AND DEPENDENCIES:\n" +
                        "            - Include necessary mock configurations\n" +
                        "            - Handle external dependencies appropriately\n" +
                        "            - Set up required test data\n" +
                        "\n" +
                        "            5. CODE QUALITY:\n" +
                        "            - Write clean, readable, and maintainable test code\n" +
                        "            - Use meaningful variable names\n" +
                        "            - Include proper error handling\n" +
                        "            - Follow consistent formatting\n" +
                        "\n" +
                        "            Generate the complete test code with all necessary components.")
                .build();
    }
}