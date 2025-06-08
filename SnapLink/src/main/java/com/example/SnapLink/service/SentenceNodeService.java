package com.example.SnapLink.service;

import com.example.SnapLink.dto.SentenceResponseNodeDTO;
import com.example.SnapLink.dto.addChildDTO;
import com.example.SnapLink.entity.SentenceNode;
import com.example.SnapLink.repository.SentenceNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class SentenceNodeService {
    @Autowired  private SentenceNodeRepository sentenceNodeRepository;

    public SentenceResponseNodeDTO getSentenceNode(String id){
        SentenceNode node = sentenceNodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found with id: " + id));

        return  SentenceResponseNodeDTO.builder()
                .id(node.getId())
                .storyId(node.getStoryId())
                .Author(node.getCreatedBy())
                .content(node.getContent())
                .likes(node.getLikes())
                .parentId(node.getParentId())
                .createdAt(node.getCreatedAt())
                .hasMoreChildren(!node.getChildIds().isEmpty())
                .build();
    }

    public List<SentenceResponseNodeDTO> getChildren(String id) {
        SentenceNode parentNode = sentenceNodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found with id: " + id));

        if(parentNode.getChildIds().isEmpty()) return new ArrayList<>();

        List<SentenceNode> children = sentenceNodeRepository.findAllById(parentNode.getChildIds());


        return children.stream()
                .map(child -> SentenceResponseNodeDTO.builder()
                        .id(child.getId())
                        .storyId(child.getStoryId())
                        .Author(child.getCreatedBy())
                        .content(child.getContent())
                        .likes(child.getLikes())
                        .parentId(child.getParentId())
                        .createdAt(child.getCreatedAt())
                        .hasMoreChildren(!child.getChildIds().isEmpty())
                        .build())
                .collect(Collectors.toList());
    }

    public SentenceResponseNodeDTO addChild(addChildDTO nodeReq){
        SentenceNode parentNode = sentenceNodeRepository.findById(nodeReq.getParentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found with id: " + nodeReq.getParentId()));

        SentenceNode node = SentenceNode.builder()
                            .content(nodeReq.getContent())
                            .createdBy(nodeReq.getUserName())
                            .storyId(nodeReq.getStoryId())
                            .parentId(nodeReq.getParentId())
                            .build();

        SentenceNode saveNode = sentenceNodeRepository.save(node);

        parentNode.getChildIds().add(saveNode.getId());

        sentenceNodeRepository.save(parentNode);

        return  SentenceResponseNodeDTO.builder()
                .id(saveNode.getId())
                .createdAt(saveNode.getCreatedAt())
                .storyId(saveNode.getStoryId())
                .Author(saveNode.getCreatedBy())
                .content(saveNode.getContent())
                .parentId(saveNode.getParentId())
                .build();

    }

    public void addLike(String id){
        SentenceNode node = sentenceNodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Node not found with id: " + id));

        node.setLikes(node.getLikes()+1);
        sentenceNodeRepository.save(node);
    }

}
