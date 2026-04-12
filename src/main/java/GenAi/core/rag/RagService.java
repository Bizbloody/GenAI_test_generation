package GenAi.core.rag;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.stereotype.Service;

/**
 * Service RAG basé uniquement sur LangChain4j.
 * Le retrieval est entièrement géré par ContentRetriever.
 */
@Service
public class RagService {

    private final ContentRetriever contentRetriever;

    public RagService(ContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    public ContentRetriever getContentRetriever() {
        return contentRetriever;
    }
}