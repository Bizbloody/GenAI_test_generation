package rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.data.segment.TextSegment;

import java.util.List;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

/*public class Rag {

    private final EmbeddingStoreContentRetriever embeddingStoreContentRetriever;

    public Rag() {
        List<Document> documents = loadDocuments("C:/Users/CHMARTINEZ/IdeaProjects/dp-ffmcc-genai-poc/src/files");
        EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();
        this.embeddingStoreContentRetriever = createContentRetriever(documents, embeddingModel);
    }

    public static EmbeddingStoreContentRetriever createContentRetriever(List<Document> documents, EmbeddingModel embeddingModel) {
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Create an EmbeddingStoreIngestor with the document splitter
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 30))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        // Ingest the documents into the store
        ingestor.ingest(documents);

        // Create and return the content retriever
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();
    }

    public EmbeddingStoreContentRetriever getEmbeddingStoreContentRetriever() {
        return embeddingStoreContentRetriever;
    }
}
*/

public class Rag {
    private static Rag instance;
    private final EmbeddingStoreContentRetriever contentRetriever;

    private List<Document> loadDocumentsFromResources(String folderName) {
        URL folderURL = getClass().getClassLoader().getResource(folderName);
        if (folderURL == null) {
            throw new RuntimeException("Folder not found in resources: " + folderName);
        }

        try {
            File folder = new File(folderURL.toURI());
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("No files found in resources/" + folderName);
            } else {
                System.out.println("Found files:");
                Arrays.stream(files).forEach(f -> System.out.println(" - " + f.getName()));
            }
            return loadDocuments(folder.getAbsolutePath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Rag() {
        System.out.println("Initializing RAG system...");
        List<Document> documents = loadDocumentsFromResources("files"); // folder inside resources
        System.out.println("Documents found: " + documents.size());
        EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();
        this.contentRetriever = createContentRetriever(documents, embeddingModel);
        System.out.println("RAG system initialized.");
    }

    public static synchronized Rag getInstance() {
        if (instance == null) {
            instance = new Rag();
        }
        return instance;
    }

    public EmbeddingStoreContentRetriever getContentRetriever() {
        return contentRetriever;
    }

    private static EmbeddingStoreContentRetriever createContentRetriever(List<Document> documents, EmbeddingModel embeddingModel) {
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 30))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(documents);

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();
    }
}