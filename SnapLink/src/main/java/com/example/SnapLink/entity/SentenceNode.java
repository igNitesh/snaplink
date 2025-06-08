package com.example.SnapLink.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;
import java.util.ArrayList;


@Document(collection = "sentenceNodes")
@Data
@Builder
public class SentenceNode {
    @Id
    private String id;

    @NotBlank(message = "Story ID cannot be blank")
    @Indexed
    private String storyId;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    private String content;

    @NotNull(message = "Created By is required")
    private String createdBy;

    @Indexed
    private String parentId; // Null for root node

    @Indexed
    @Builder.Default
    private List<String> childIds = new ArrayList<>();

    @NotNull
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private int likes = 0;
}
