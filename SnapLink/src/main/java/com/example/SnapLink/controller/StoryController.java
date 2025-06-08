package com.example.SnapLink.controller;

import com.example.SnapLink.dto.*;
import com.example.SnapLink.service.SentenceNodeService;
import com.example.SnapLink.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {
    @Autowired private StoryService storyService;
    @Autowired private SentenceNodeService sentenceNodeService;

    @PostMapping("/create")
    public ResponseEntity<StoryResponseDTO> createStory(@RequestBody StoryCreateRequest storyCreateRequest, Principal principal) {
        String username = principal.getName();
        StoryResponseDTO createdStory = storyService.createStory(storyCreateRequest, username);

        return  ResponseEntity.ok(createdStory);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStory() {
        List<StoryResponseDTO> stories = storyService.getAllStories();
        return  ResponseEntity.ok(stories);
    }

    @PostMapping("/addChild")
    public ResponseEntity<SentenceResponseNodeDTO> addChild(@RequestBody  addChildDTO request, Principal principal){
        request.setUserName(principal.getName());
        SentenceResponseNodeDTO childNode = sentenceNodeService.addChild(request);
        return ResponseEntity.ok(childNode);
    }

    @GetMapping("/{storyId}")
    public  ResponseEntity<StoryResponseDTO> getStoryById(@PathVariable String storyId){
        StoryResponseDTO story = storyService.getStoryById(storyId);
        return ResponseEntity.ok(story);
    }

    @GetMapping("/node/{nodeId}/children")
     public  ResponseEntity<List<SentenceResponseNodeDTO>> getChildren(@PathVariable String nodeId){
        List<SentenceResponseNodeDTO> children = sentenceNodeService.getChildren(nodeId);
        return  ResponseEntity.ok(children);
    }

//    @PostMapping("/add_like")
//    public ResponseEntity<String> addLike(@RequestBody String nodeId){
//        sentenceNodeService.addLike(nodeId);
//        return  ResponseEntity.ok("ok");
//    }

}
