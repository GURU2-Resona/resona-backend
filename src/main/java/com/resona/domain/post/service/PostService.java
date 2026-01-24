package com.resona.domain.post.service;

import com.resona.domain.post.dto.PostCreateRequest;

public interface PostService {
    /**
     * 추천글 작성
     * @param memberId 작성자 ID
     * @param request 작성 요청 데이터 (노래 정보 + 글 + 태그)
     * @return 생성된 Post ID
     */
    Long createPost(Long memberId, PostCreateRequest request);
}