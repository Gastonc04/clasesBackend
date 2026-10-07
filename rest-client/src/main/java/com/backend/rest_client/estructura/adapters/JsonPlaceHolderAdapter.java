package com.backend.rest_client.estructura.adapters;

import com.backend.rest_client.domain.mocker.adapters.PostAdapter;
import com.backend.rest_client.domain.mocker.model.Post;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class JsonPlaceHolderAdapter implements PostAdapter {

    private static final ParameterizedTypeReference<List<Post>> POST_TYPE =
            new ParameterizedTypeReference<>() {};

    RestClient restClient;

    @Override
    public List<Post> list() {
        try {
            val posts = restClient.get()
                    .uri("/posts")
                    .retrieve()
                    .body(POST_TYPE);

            return posts;

        } catch (RestClientException e) {
            log.error("Error al obtener posts", e);
            throw e;
        }
    }
}
