package com.example.SnapLink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class addChildDTO {
    private String storyId;
    private String parentId;
    @NotBlank(message = "content is required")
    private String content;
    private String userName;
}
