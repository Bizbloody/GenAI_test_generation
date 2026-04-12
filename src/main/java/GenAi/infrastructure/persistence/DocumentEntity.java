package GenAi.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "documents")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "vector")
    private float[] embedding;

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }
}