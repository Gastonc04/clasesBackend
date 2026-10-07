package com.backend.rest_client.application.responses;

import com.backend.rest_client.domain.mocker.model.Post;

public record PostResponse(Integer id, String title) {

    public static PostResponse of(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle()
        );
    }
}
