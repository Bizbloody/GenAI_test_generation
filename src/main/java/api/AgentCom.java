package api;

import agents.code_feedback.CodeFeedback;
import agents.code_generator.CodeGenerator;
import agents.documentation_parser.DocumentParser;
import io.github.cdimascio.dotenv.Dotenv;



public class AgentCom {

    public static void main(String[] args) {

        //Azure

/*
        // Chargement des variables d'environnement comme avant
        Dotenv dotenv = Dotenv.load();

        // Création des configurations pour chaque agent
        // Create configurations for different APIs
        ApiConfiguration codeGenConfig = new ApiConfiguration(
                dotenv.get("AZURE_OPENAI_ENDPOINT_CODE_GENERATOR"),
                dotenv.get("AZURE_OPENAI_API_KEY_CODE_GENERATOR"),
                dotenv.get("AZURE_OPENAI_DEPLOYMENT_ID_CODE_GENERATOR"),
                dotenv.get("AZURE_OPENAI_API_VERSION_CODE_GENERATOR")
        );

        ApiConfiguration codeFeedbackConfig = new ApiConfiguration(
                dotenv.get("AZURE_OPENAI_ENDPOINT_FEEDBACK_AGENT"),
                dotenv.get("AZURE_OPENAI_API_KEY_FEEDBACK_AGENT"),
                dotenv.get("AZURE_OPENAI_DEPLOYMENT_ID_FEEDBACK_AGENT"),
                dotenv.get("AZURE_OPENAI_API_VERSION_FEEDBACK_AGENT")
        );
        ApiConfiguration parserConfig = new ApiConfiguration(
                dotenv.get("AZURE_OPENAI_ENDPOINT_DOCUMENTATION_PARSER"),
                dotenv.get("AZURE_OPENAI_API_KEY_DOCUMENTATION_PARSER"),
                dotenv.get("AZURE_OPENAI_DEPLOYMENT_ID_DOCUMENTATION_PARSER"),
                dotenv.get("AZURE_OPENAI_API_VERSION_DOCUMENTATION_PARSER")
        );

 */


        Dotenv dotenv = Dotenv.load();

        ApiConfiguration codeGenConfig = new ApiConfiguration(
                dotenv.get("baseUrl"),
                dotenv.get("apiKey"),
                dotenv.get("modelName")
        );
        System.out.println("\nApi config code gen done");

        ApiConfiguration codeFeedbackConfig = new ApiConfiguration(
                dotenv.get("baseUrl"),
                dotenv.get("apiKey"),
                dotenv.get("modelName")
        );
        System.out.println("\nApi config code feedback done");

        ApiConfiguration parserConfig = new ApiConfiguration(
                dotenv.get("baseUrl"),
                dotenv.get("apiKey"),
                dotenv.get("modelName")
        );
        System.out.println("\nApi config parser done");

        // Create agents with their specific configurations
        CodeGenerator codeGenerator = new CodeGenerator(codeGenConfig);
        Assistant codeGeneratorAssistant = codeGenerator.creationAgentCodeGenerator();
        System.out.println("\nagent code gen done");

        CodeFeedback codeFeedback = new CodeFeedback(codeFeedbackConfig);
        Assistant codeFeedbackAssistant =  codeFeedback.creationAgentCodeFeedback();
        System.out.println("\nagent code feedback done");

        DocumentParser documentParser = new DocumentParser(parserConfig);
        Assistant documentParserAssistant = documentParser.creationAgentDocumentParser();
        System.out.println("\nagent parser done");

        // Création du processeur de workflow
        Communication processor = new Communication(
                codeGeneratorAssistant,
                codeFeedbackAssistant,
                documentParserAssistant
        );
        System.out.println("\nworkflow creer");

        // Utilisation du processeur
        String finalCode = processor.processCodeRequest();


    }
}
