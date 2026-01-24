package com.resona.domain.post.service;

import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;

public interface PostService {
    /**
     * 추천글 작성
     * @return 생성된 게시글의 상세 정보 (PostResponse)
     */
    PostCreateResponse createPost(Long memberId, PostCreateRequest request);
}