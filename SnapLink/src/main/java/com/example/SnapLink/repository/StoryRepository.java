package com.example.SnapLink.repository;

import com.example.SnapLink.entity.Story;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoryRepository extends MongoRepository<Story, String> {
    List<Story> findByCreatedBy(String authorId);
}
