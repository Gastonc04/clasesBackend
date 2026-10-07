package com.backend.rest_client.application;

import com.backend.rest_client.application.responses.PostResponse;
import com.backend.rest_client.domain.mocker.adapters.PostAdapter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RestController
@RequestMapping("/api/v1/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostService {

    PostAdapter postAdapter;

    public List<PostResponse> list() {
        return postAdapter.list()
                .stream()
                .map(PostResponse::of)
                .toList();
    }
}
