# Test Generation Assistant Project

Ce projet est un système multi-agents utilisant l'IA pour générer, réviser et optimiser du code de test. Il utilise langchain4j avec Groq LLM (Azure est dans le code mais pas test) et intègre un système RAG (Retrieval Augmented Generation) pour enrichir les réponses avec de la documentation pertinente.

### 🛠 Technologies Utilisées
Java 17+\
langchain4j\
Groq LLM\
AllMiniLmL6V2\
SLF4J
### 📋 Prérequis
JDK 17 ou supérieur\
Maven\
Une clé API Groq\
Documentation à indexer dans le dossier spécifié
### 🔧 Installation
Clonez le repository :

    git clone [url-du-repo]

Configurez votre fichier .env à la racine du projet :

    GROQ_API_KEY=votre_clé_api_groq 
        
    GROQ_MODEL=le model que vous utilisé

Installez les dépendances avec Maven :

mvn clean install

### 🚀 Utilisation
Placez votre documentation dans le dossier spécifié pour le RAG :
/home/langchain4j/documentation/

Exécutez le programme :

    public class AgentCom {
        public static void main(String[] args) {
    
            // ... configuration ...
            String finalCode = workflow.processCodeRequest(
            "Create tests for user authentication API"
            );
        }
    }

### 🔄 Workflow

Le système suit un processus itératif :

Document Parser : Analyse la documentation et le code existant

Code Generator : Génère le code

Code Feedback : Review le code avec contexte RAG

### 📦 Dépendances Maven:

    <dependencies>
    <!-- langchain4j -->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j</artifactId>
        <version>1.1.0</version>
    </dependency>

    <!-- Embeddings -->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-embeddings-all-minilm-l6-v2</artifactId>
        <version>1.1.0-beta7</version>
    </dependency>

    <!-- Azure langchain (pour azure) -->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-azure-open-ai</artifactId>
        <version>1.1.0-rc1</version>
    </dependency>

    <!-- .env in java -->
    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>dotenv-java</artifactId>
        <version>3.2.0</version>
    </dependency>

    <!-- openai (pour groq)-->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-open-ai</artifactId>
        <version>1.1.0</version>
    </dependency>

    <!-- Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.7</version>
    </dependency>
    </dependencies>

### ✨ Améliorations possibles

[ ] Interface utilisateur web ou intégration ide

[ ] Persistance des données

[ ] Amélioration des prompts

[ ] Multiple sessions

[ ] Use LangGraph4j instead to create the agents communication

[ ] utilisation du stockage cloud pour le rag avec azure Postgresql et donc utilisation de pgvector

### 🐛 Résolution des Problèmes Courants

Assurez-vous que toutes les dépendences Langchain4j sont de la même version

### 📚 Documentation Additionnelle

langchain4j Documentation
