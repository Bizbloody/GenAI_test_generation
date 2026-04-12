package GenAi.core.agents.code_feedback;

import GenAi.core.api.Assistant;
import GenAi.core.rag.RagService;
import GenAi.infrastructure.llm.FoundryLlmService;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;

import org.springframework.stereotype.Component;

@Component
public class CodeFeedback {

    private final FoundryLlmService foundryLlmService;
    private final RagService ragService;

    public CodeFeedback(FoundryLlmService foundryLlmService, RagService ragService) {

        this.foundryLlmService = foundryLlmService;
        this.ragService = ragService;
    }

    public Assistant creationAgentCodeFeedback() {

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        return AiServices.builder(Assistant.class)
                .chatModel(foundryLlmService.getChatModel()) // ✅ propre
                .chatMemory(chatMemory)
                .contentRetriever(ragService.getContentRetriever())
                .systemMessageProvider(chatMemoryId -> "As a test code reviewer, analyze the following generated test code against the documentation and requirements.\n" +
                        "\n" +
                        "            GENERATED TEST CODE:\n" +
                        "            %s\n" +
                        "\n" +
                        "            ORIGINAL ANALYSIS AND REQUIREMENTS:\n" +
                        "            %s\n" +
                        "\n" +
                        "            Please provide a comprehensive review focusing on:\n" +
                        "\n" +
                        "            1. COMPLETENESS:\n" +
                        "            - Are all required test scenarios covered?\n" +
                        "            - Are critical paths and edge cases tested?\n" +
                        "            - Are all integration points properly tested?\n" +
                        "            - Is there sufficient error handling?\n" +
                        "\n" +
                        "            2. CORRECTNESS:\n" +
                        "            - Are the tests properly structured?\n" +
                        "            - Are assertions appropriate and complete?\n" +
                        "            - Are mocks and test data properly configured?\n" +
                        "            - Is the AAA pattern correctly followed?\n" +
                        "\n" +
                        "            3. BEST PRACTICES:\n" +
                        "            - Does the code follow framework best practices?\n" +
                        "            - Is the code clean and maintainable?\n" +
                        "            - Are naming conventions followed?\n" +
                        "            - Is documentation sufficient?\n" +
                        "\n" +
                        "            4. TECHNICAL DEBT:\n" +
                        "            - Are there any potential maintenance issues?\n" +
                        "            - Is there unnecessary complexity?\n" +
                        "            - Are there any performance concerns?\n" +
                        "            - Are there any security considerations?\n" +
                        "\n" +
                        "            Please provide your response in the following JSON format:\n" +
                        "            {\n" +
                        "                \"status\": \"ACCEPTED\" or \"NEEDS_REVISION\",\n" +
                        "                \"review\": {\n" +
                        "                    \"completeness\": {\n" +
                        "                        \"issues\": [],\n" +
                        "                        \"suggestions\": []\n" +
                        "                    },\n" +
                        "                    \"correctness\": {\n" +
                        "                        \"issues\": [],\n" +
                        "                        \"suggestions\": []\n" +
                        "                    },\n" +
                        "                    \"bestPractices\": {\n" +
                        "                        \"issues\": [],\n" +
                        "                        \"suggestions\": []\n" +
                        "                    },\n" +
                        "                    \"technicalDebt\": {\n" +
                        "                        \"issues\": [],\n" +
                        "                        \"suggestions\": []\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"summary\": \"Brief overview of the review\",\n" +
                        "                \"actionItems\": [\n" +
                        "                    \"Prioritized list of required changes\"\n" +
                        "                ]")
                .build();
    }
}