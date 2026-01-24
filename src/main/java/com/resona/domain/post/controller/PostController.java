package com.resona.domain.post.controller;

import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> createPost(
            // 실제로는 @AuthenticationPrincipal 등으로 로그인한 유저 ID를 가져와야 함.
            // 여기서는 테스트를 위해 임시로 헤더나 파라미터로 가정하거나 1L로 고정
            @RequestHeader("X-USER-ID") Long memberId,
            @RequestBody PostCreateRequest request
    ) {
        Long postId = postService.createPost(memberId, request);
        return ResponseEntity.ok(postId);
    }
}