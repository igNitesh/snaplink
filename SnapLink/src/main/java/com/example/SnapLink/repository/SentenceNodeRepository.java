package com.example.SnapLink.repository;

import com.example.SnapLink.entity.SentenceNode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SentenceNodeRepository extends MongoRepository<SentenceNode,String> {
}
