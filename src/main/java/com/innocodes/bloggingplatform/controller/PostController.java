package com.innocodes.bloggingplatform.controller;

import com.innocodes.bloggingplatform.model.dto.PostDto;
import com.innocodes.bloggingplatform.model.dto.UpdatePostDto;
import com.innocodes.bloggingplatform.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class PostController {

    private final PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<Object> createPost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok((postService.createPost(postDto)));
    }


    @GetMapping("/get-all-posts")
    public ResponseEntity<Object> getAllPosts() {
        return ResponseEntity.ok((postService.getAllPosts()));
    }


    @GetMapping("/get-post/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok((postService.getPostById(id)));
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<Object> deletePostById(@PathVariable Long id) {
        return ResponseEntity.ok((postService.deletePostById(id)));
    }

    @PutMapping("/update-post/{id}")
    public ResponseEntity<Object> updatePostById(@PathVariable Long id, @RequestBody UpdatePostDto postDto) {
        return ResponseEntity.ok((postService.updatePost(id, postDto)));
    }
}
