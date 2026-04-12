package GenAi.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Communication {
    // 1. Déclare le logger
    private static final Logger log = LoggerFactory.getLogger(Communication.class);

    private final Assistant codeGeneratorAssistant;
    private final Assistant codeFeedbackAssistant;
    private final Assistant documentParserAssistant;
    private static final int MAX_ITERATIONS = 5;

    public Communication(
            @Qualifier("codeGeneratorAssistant") Assistant codeGeneratorAssistant,
            @Qualifier("codeFeedbackAssistant") Assistant codeFeedbackAssistant,
            @Qualifier("documentParserAssistant") Assistant documentParserAssistant
    ) {
        this.codeGeneratorAssistant = codeGeneratorAssistant;
        this.codeFeedbackAssistant = codeFeedbackAssistant;
        this.documentParserAssistant = documentParserAssistant;
    }

    public String processCodeRequest(String codeInput) {
        log.info("Démarrage du processCodeRequest...");

        // Sécurité input
        if (codeInput == null || codeInput.isBlank()) {
            log.error("Input code est vide !");
            return "Error: Code input is empty";
        }

        // Step 1: Parse documentation
        String parsedDocumentation = documentParserAssistant.chat(
                "Parse the documentation of the test framework:"
        );

        // C'est ici que ça plante si parsedDocumentation est vide !
        log.info("Documentation récupérée : {}", (parsedDocumentation != null ? "OUI" : "NON"));

        if (parsedDocumentation == null || parsedDocumentation.isBlank()) {
            log.warn("La documentation est vide, la génération risque d'échouer.");
            parsedDocumentation = "No specific documentation found.";
        }

        log.info("doc parsed. {}", parsedDocumentation);

        // Step 2: Generate code
        String currentCode = codeGeneratorAssistant.chat(
                "Create tests for the following code:\n" + codeInput +
                        "\nUsing this documentation:\n" + parsedDocumentation
        );

        log.info("Premier jet de code généré. {}", currentCode);

        int iterations = 0;
        while (iterations < MAX_ITERATIONS) {
            log.info("Début itération {}", iterations);

            String reviewFeedback = codeFeedbackAssistant.chat(
                    "Review this code based on documentation. Say 'ok' if valid:\n"
                            + currentCode + "\n" + parsedDocumentation
            );

            log.info("{}Feedback reçu : {}", iterations, reviewFeedback);

            if (reviewFeedback.toLowerCase().contains("ok")) {
                log.info("Feedback OK reçu, arrêt des itérations.");
                return currentCode;
            }

            currentCode = codeGeneratorAssistant.chat(
                    "Improve this code based on feedback:\n"
                            + currentCode + "\nFeedback:\n" + reviewFeedback
            );

            log.info("{} jet de code généré. {}", iterations, currentCode);

            iterations++;
        }

        return currentCode;
    }
}