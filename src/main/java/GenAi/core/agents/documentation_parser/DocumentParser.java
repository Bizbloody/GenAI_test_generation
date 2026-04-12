package GenAi.core.agents.documentation_parser;

import GenAi.core.api.Assistant;
import GenAi.core.rag.RagService;
import GenAi.infrastructure.llm.FoundryLlmService;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;

import org.springframework.stereotype.Component;

@Component
public class DocumentParser {

    private final FoundryLlmService foundryLlmService;
    private final RagService ragService;

    public DocumentParser(FoundryLlmService foundryLlmService, RagService ragService) {

        this.foundryLlmService = foundryLlmService;
        this.ragService = ragService;
    }

    public Assistant creationAgentDocumentParser() {

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        return AiServices.builder(Assistant.class)
                .chatModel(foundryLlmService.getChatModel()) // ✅ propre
                .chatMemory(chatMemory)
                .contentRetriever(ragService.getContentRetriever())
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
