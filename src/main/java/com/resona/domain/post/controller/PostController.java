package com.resona.domain.post.controller;

import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;
import com.resona.domain.post.service.PostService;
import com.resona.global.response.ApiResponse;
import com.resona.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "추천글 관련 API")
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "추천글 작성", description = "추천글을 작성합니다.")
    @PostMapping("")
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost( // ApiResponse<Long> -> ApiResponse<PostResponse>
                                                                       @RequestHeader("X-USER-ID") Long memberId,
                                                                       @RequestBody @Valid PostCreateRequest request
    ) {
        PostCreateResponse response = postService.createPost(memberId, request);

        return ResponseEntity.status(SuccessCode.POST_SAVE_OK.getHttpStatus())
                .body(ApiResponse.onSuccess(SuccessCode.POST_SAVE_OK, response));
    }
}