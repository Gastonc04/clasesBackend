package com.backend.rest_client.application.controllers;

import com.backend.rest_client.application.PostService;
import com.backend.rest_client.application.responses.PostResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostController {

    PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> list() {
        List<PostResponse> list = postService.list();

        return ResponseEntity.ok(list);
    }
}
