package com.resona.domain.post.service;

import com.resona.domain.member.entity.Member;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;
import com.resona.domain.post.dto.PostDetailResponse;
import com.resona.domain.post.dto.PostListResponse;
import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.PostScrap;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import com.resona.domain.post.repository.PostRepository;
import com.resona.domain.post.repository.PostScrapRepository;
import com.resona.global.exception.GlobalException;
import com.resona.global.oAuth.JwtProvider;
import com.resona.global.response.ErrorCode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final PostScrapRepository postScrapRepository;
  private final JwtProvider jwtProvider;

  @Override
  @Transactional
  public PostCreateResponse createPost(String token, PostCreateRequest request) {
    Long memberId = getMemberIdByAccessToken(token);
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

    Post post =
        Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .songTitle(request.getSongTitle())
            .songUrl(request.getSongUrl())
            .member(member)
            .saved(false)
            .category(request.getCategory())
            .customCategory(
                request.getCategory() == Category.OTHER ? request.getCustomCategory() : null)
            .scene(request.getScene())
            .customScene(request.getScene() == Scene.OTHER ? request.getCustomScene() : null)
            .build();

    Post savedPost = postRepository.save(post);

    return PostCreateResponse.of(savedPost);
  }

  @Override
  @Transactional
  public boolean scrapPost(String token, Long postId) {
    Long memberId = getMemberIdByAccessToken(token);
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

    Optional<PostScrap> scrapOptional = postScrapRepository.findByMemberAndPost(member, post);

    if (scrapOptional.isPresent()) {
      postScrapRepository.delete(scrapOptional.get());
      return false;
    } else {
      PostScrap postScrap = PostScrap.createScrap(member, post);
      postScrapRepository.save(postScrap);
      return true;
    }
  }

  // 목록 조회
  @Override
  public List<PostListResponse> getPosts(Category category, Scene scene) {
    // DB에서 필터링된 게시글 목록 조회
    List<Post> posts = postRepository.findAllByFilters(category, scene);

    // Entity 리스트를 DTO 리스트로 변환
    return posts.stream().map(PostListResponse::of).collect(Collectors.toList());
  }

  // 상세 조회
  @Override
  public PostDetailResponse getPostDetail(String token, Long postId) {
    Long memberId = getMemberIdByAccessToken(token);
    // 게시글 조회 (작성자 정보 포함 fetch join)
    Post post =
        postRepository
            .findByIdWithMember(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

    // 현재 조회를 요청한 사용자 조회 (스크랩 여부 확인용)
    Member currentMember =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

    // 스크랩 여부 확인 (exists 쿼리 사용)
    boolean isSaved = postScrapRepository.existsByMemberAndPost(currentMember, post);

    // 내 글인지 확인 (현재 로그인한 ID와 게시글 작성자 ID 비교)
    boolean isMine = post.getMember().getId().equals(memberId);

    // DTO 변환 및 반환
    return PostDetailResponse.of(post, isSaved, isMine);
  }

  // 내가 저장한 추천글 목록 조회 구현
  @Override
  public List<PostListResponse> getScrappedPosts(String token, Category category, Scene scene) {
    Long memberId = getMemberIdByAccessToken(token);
    // 사용자 존재 확인
    if (!memberRepository.existsById(memberId)) {
      throw new GlobalException(ErrorCode.NOT_FOUND);
    }

    // Repository에서 스크랩한 글 목록 조회 (필터링 포함)
    List<Post> scrappedPosts = postRepository.findAllScrappedByFilters(memberId, category, scene);

    // DTO 변환 (기존 PostListResponse 재사용)
    return scrappedPosts.stream().map(PostListResponse::of).collect(Collectors.toList());
  }

  // 특정 작성자의 글 목록 조회 구현
  @Override
  public List<PostListResponse> getMemberPosts(Long writerId, Category category, Scene scene) {
    // 작성자가 실제로 존재하는지 확인
    if (!memberRepository.existsById(writerId)) {
      throw new GlobalException(ErrorCode.NOT_FOUND);
    }

    // 해당 작성자의 글 목록 조회 (필터링 포함)
    List<Post> memberPosts = postRepository.findAllByWriterIdAndFilters(writerId, category, scene);

    // DTO 변환
    return memberPosts.stream().map(PostListResponse::of).collect(Collectors.toList());
  }

    @Override
    public List<PostListResponse> getMyPosts(String token, Category category, Scene scene) {
        Long memberId = getMemberIdByAccessToken(token);
        if (!memberRepository.existsById(memberId)) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        // 해당 작성자의 글 목록 조회 (필터링 포함)
        List<Post> memberPosts = postRepository.findAllByWriterIdAndFilters(memberId, category, scene);

        // DTO 변환
        return memberPosts.stream().map(PostListResponse::of).collect(Collectors.toList());
    }

    private Long getMemberIdByAccessToken(String token) {
    String accessToken = token.split(" ")[1];
    return jwtProvider.getMemberId(accessToken);
  }
}
