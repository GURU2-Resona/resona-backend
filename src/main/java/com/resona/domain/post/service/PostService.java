package com.resona.domain.post.service;

import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;
import com.resona.domain.post.dto.PostDetailResponse;
import com.resona.domain.post.dto.PostListResponse;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import java.util.List;

public interface PostService {
  /**
   * 추천글 작성
   *
   * @return 생성된 게시글의 상세 정보 (PostResponse)
   */
  PostCreateResponse createPost(String token, PostCreateRequest request);

  /**
   * 추천글 스크랩 (저장/취소 )
   *
   * @return true: 저장됨, false: 저장 취소
   */
  boolean scrapPost(String token, Long postId);

  List<PostListResponse> getPosts(Category category, Scene scene);

  // 상세 조회 메서드
  PostDetailResponse getPostDetail(String token, Long postId);

  // 내가 저장한 글 목록 조회
  List<PostListResponse> getScrappedPosts(String token, Category category, Scene scene);

  // 특정 작성자의 글 목록 조회
  List<PostListResponse> getMemberPosts(Long writerId, Category category, Scene scene);
}
