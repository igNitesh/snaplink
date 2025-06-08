package com.example.SnapLink.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "stories")
@Data
@Builder
public class Story  {
    @Id
    private String id;

    @NotBlank(message = "Title is required")
    @Indexed(unique = true) // Unique title per user (Week 1, Task 6)
    private String title;

    @Field("createdBy")
    @NotBlank(message = "Created by is required")
    @Indexed
    private String createdBy;

    private String rootSentenceId; // Links to root SentenceNodeModel

    @Builder.Default
    private Instant createdAt = Instant.now();

    private String description; // Story metadata (optional)

    @Builder.Default
    private int totalLikes = 0; // For Week 5 leaderboards

    @Builder.Default
    private int nodeCount = 1; // Tracks total nodes (recommended for large trees)
}

