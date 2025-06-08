package com.example.SnapLink.service;

import com.example.SnapLink.dto.SentenceResponseNodeDTO;
import com.example.SnapLink.dto.StoryCreateRequest;
import com.example.SnapLink.dto.StoryResponseDTO;
import com.example.SnapLink.entity.SentenceNode;
import com.example.SnapLink.entity.Story;
import com.example.SnapLink.repository.SentenceNodeRepository;
import com.example.SnapLink.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired  private SentenceNodeRepository sentenceNodeRepository;
    @Autowired  private SentenceNodeService sentenceNodeService;

    public StoryResponseDTO createStory(StoryCreateRequest request, String userName){

        // rootSentence
        SentenceNode rootNode = SentenceNode.builder()
                .content(request.getRootSentence())
                .createdBy(userName)
                .build();

        SentenceNode savedRootNode = sentenceNodeRepository.save(rootNode);

        Story story = Story.builder()
                .title(request.getTitle())
                .createdBy(userName)
                .description(request.getDescription())
                .rootSentenceId(savedRootNode.getId())
                .build();
        Story savedStory = storyRepository.save(story);

        //update root sentence
        savedRootNode.setStoryId(savedRootNode.getStoryId());
        sentenceNodeRepository.save(savedRootNode);

        return  StoryResponseDTO.builder()
                .id(savedStory.getId())
                .title(savedStory.getTitle())
                .description(savedStory.getDescription())
                .createdBy(savedStory.getCreatedBy())
                .createdAt(savedStory.getCreatedAt())
                .totalLikes(savedRootNode.getLikes())
                .rootSentence(sentenceNodeService.getSentenceNode(savedStory.getRootSentenceId()))
                .nodeCount(savedStory.getNodeCount())
                .build();
    }

    // get all stories
    public List<StoryResponseDTO> getAllStories(){

        List<Story> stories = storyRepository.findAll();

        return stories.stream()
                .map(story -> StoryResponseDTO.builder()
                        .id(story.getId())
                        .title(story.getTitle())
                        .description(story.getDescription())
                        .createdBy(story.getCreatedBy())
                        .createdAt(story.getCreatedAt())
                        .totalLikes(story.getTotalLikes())
                        .rootSentence(sentenceNodeService.getSentenceNode(story.getRootSentenceId())) // -> it should be content as of id for test
                        .nodeCount(story.getNodeCount())
                        .build())
                .collect(Collectors.toList());
    }


    // get storyById with rootNode children
    public  StoryResponseDTO getStoryById(String id){
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No story found!"));

        SentenceResponseNodeDTO rootNode = sentenceNodeService.getSentenceNode(story.getRootSentenceId());
        List<SentenceResponseNodeDTO> children;

        if(rootNode.isHasMoreChildren()){
            children = sentenceNodeService.getChildren(story.getRootSentenceId());
            rootNode.setChildren(children);
        }

        return  StoryResponseDTO.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .createdBy(story.getCreatedBy())
                .createdAt(story.getCreatedAt())
                .totalLikes(story.getTotalLikes())
                .rootSentence(rootNode)
                .nodeCount(story.getNodeCount())
                .build();

    }


}