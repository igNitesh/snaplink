package com.example.SnapLink.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class SentenceResponseNodeDTO {
    private String id;

    private String storyId;

    private String content;

    private String Author;

    private String parentId; // Null for root node

    private Instant createdAt;

    private int likes = 0;

    private boolean hasMoreChildren; // Optional: only used in children nodes

    private List<SentenceResponseNodeDTO> children;
}
