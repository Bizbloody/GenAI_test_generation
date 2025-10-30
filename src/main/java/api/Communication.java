// ProcessCodeWorkflow.java
package api;

public class Communication {
    private final Assistant codeGeneratorAssistant;
    private final Assistant codeFeedbackAssistant;
    private final Assistant documentParserAssistant;
    private static final int MAX_ITERATIONS = 5;

    public Communication(
            Assistant codeGeneratorAssistant,
            Assistant codeFeedbackAssistant,
            Assistant documentParserAssistant) {
        this.codeGeneratorAssistant = codeGeneratorAssistant;
        this.codeFeedbackAssistant = codeFeedbackAssistant;
        this.documentParserAssistant = documentParserAssistant;

    }

    public String processCodeRequest() {
        String currentCode = null;
        String documentation = null;
        int iterations = 0;

        // Step 1: Parced the documentation
        String parsedDocumentation = documentParserAssistant.chat("parse the docummentation for the test framework:");
        System.out.println("\nParsed documentation: ");
        System.out.println(parsedDocumentation);

        // Step 2: Generate Code
        currentCode = codeGeneratorAssistant.chat(
                "Create the test based on the parsed documentation and the code :\n"
        );
        System.out.println("\nGenerated Code (Iteration 0):");
        System.out.println(currentCode);

        while (iterations < MAX_ITERATIONS) {

            // Step 3: Review Code
            String reviewFeedback = codeFeedbackAssistant.chat("Review this code via the parsed documentation, if it's good and respect everything say ok:\n" + currentCode + parsedDocumentation);
            System.out.println("\nCode Review Feedback:");   // needs to have just ok
            System.out.println(reviewFeedback);

            // Step 4: Generate Code
            currentCode = codeGeneratorAssistant.chat( "Improve this code based on feedback:\n" + currentCode + reviewFeedback);
            System.out.println("\nGenerated Code (Iteration " + (iterations + 1) + "):");
            System.out.println(currentCode);

            // Check if code is acceptable
            if (reviewFeedback.toLowerCase().contains("ok")) {
                System.out.println("\nCode completed successfully!");
                return currentCode;
            }

            iterations++;
        }

        System.out.println("\nReached maximum iterations. Returning last version.");
        return currentCode;
    }
}
