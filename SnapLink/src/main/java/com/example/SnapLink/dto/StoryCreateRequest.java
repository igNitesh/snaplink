package com.example.SnapLink.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Builder
public class StoryCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Created by is required")
    private String rootSentence; // Links to root SentenceNodeModel


    private String description; // Story metadata (optional)


}
