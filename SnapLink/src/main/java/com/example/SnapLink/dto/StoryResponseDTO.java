package com.example.SnapLink.dto;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class StoryResponseDTO {
    private String id;

    private String title;

    private String createdBy;

    private SentenceResponseNodeDTO rootSentence; // Links to root SentenceNodeModel

    private Instant createdAt ;

    private String description; // Story metadata (optional)

    private int totalLikes = 0; // For Week 5 leaderboards

    private int nodeCount = 0;  // total sentence node


}
