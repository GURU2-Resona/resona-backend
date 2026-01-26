package com.resona.domain.post.controller;

import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;
import com.resona.domain.post.dto.PostDetailResponse;
import com.resona.domain.post.dto.PostListResponse;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import com.resona.domain.post.service.PostService;
import com.resona.global.response.ApiResponse;
import com.resona.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "추천글 관련 API")
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @Operation(summary = "추천글 작성", description = "추천글을 작성합니다.")
  @PostMapping("")
  public ResponseEntity<ApiResponse<PostCreateResponse>>
      createPost( // ApiResponse<Long> -> ApiResponse<PostResponse>
                  @RequestHeader("Authorization") String token, @RequestBody @Valid PostCreateRequest request) {
    PostCreateResponse response = postService.createPost(token, request);

    return ResponseEntity.status(SuccessCode.POST_SAVE_OK.getHttpStatus())
        .body(ApiResponse.onSuccess(SuccessCode.POST_SAVE_OK, response));
  }

  @Operation(summary = "추천글 저장/취소", description = "게시글을 보관함에 저장하거나 취소합니다.")
  @PostMapping("/{postId}/scrap")
  public ResponseEntity<ApiResponse<String>> scrapPost(
          @RequestHeader("Authorization") String token, @PathVariable Long postId) {
    boolean isScraped = postService.scrapPost(token, postId);

    if (isScraped) {
      return ResponseEntity.status(SuccessCode.POST_SCRAP_OK.getHttpStatus())
          .body(ApiResponse.onSuccess(SuccessCode.POST_SCRAP_OK, "스크랩 성공"));
    } else {
      return ResponseEntity.status(SuccessCode.POST_UNSCRAP_OK.getHttpStatus())
          .body(ApiResponse.onSuccess(SuccessCode.POST_UNSCRAP_OK, "스크랩 취소"));
    }
  }

  @Operation(
      summary = "추천글 목록 조회",
      description = "필터링(category, scene)을 적용하여 목록을 조회합니다. 파라미터가 없으면 전체 목록이 조회됩니다.")
  @GetMapping("")
  public ResponseEntity<ApiResponse<List<PostListResponse>>> getPosts(
      @RequestParam(required = false) Category category,
      @RequestParam(required = false) Scene scene) {
    List<PostListResponse> response = postService.getPosts(category, scene);
    return ResponseEntity.status(SuccessCode.OK.getHttpStatus())
        .body(ApiResponse.onSuccess(SuccessCode.OK, response));
  }

  @Operation(summary = "추천글 상세 조회", description = "추천글의 상세 정보를 조회합니다. (스크랩 여부, 본인 글 여부 포함)")
  @GetMapping("/{postId}")
  public ResponseEntity<ApiResponse<PostDetailResponse>> getPostDetail(
          @RequestHeader("Authorization") String token, @PathVariable Long postId) {

    PostDetailResponse response = postService.getPostDetail(token, postId);

    return ResponseEntity.status(SuccessCode.OK.getHttpStatus())
        .body(ApiResponse.onSuccess(SuccessCode.OK, response));
  }

  @Operation(
      summary = "내가 저장한 추천글 목록 조회",
      description = "내가 스크랩한 글들의 목록을 조회합니다. 카테고리와 상황으로 필터링이 가능합니다.")
  @GetMapping("/scraps")
  public ResponseEntity<ApiResponse<List<PostListResponse>>> getScrappedPosts(
          @RequestHeader("Authorization") String token,
      @RequestParam(required = false) Category category,
      @RequestParam(required = false) Scene scene) {

    List<PostListResponse> response = postService.getScrappedPosts(token, category, scene);

    return ResponseEntity.status(SuccessCode.OK.getHttpStatus())
        .body(ApiResponse.onSuccess(SuccessCode.OK, response));
  }

  @Operation(
      summary = "특정 사용자의 추천글 목록 조회",
      description = "특정 사용자(writerId)가 작성한 추천글 목록을 조회합니다. 카테고리와 상황으로 필터링이 가능합니다.")
  @GetMapping("/members/{writerId}")
  public ResponseEntity<ApiResponse<List<PostListResponse>>> getMemberPosts(
      @PathVariable("writerId") Long writerId,
      @RequestParam(required = false) Category category,
      @RequestParam(required = false) Scene scene) {

    List<PostListResponse> response = postService.getMemberPosts(writerId, category, scene);

    return ResponseEntity.status(SuccessCode.OK.getHttpStatus())
        .body(ApiResponse.onSuccess(SuccessCode.OK, response));
  }
}
