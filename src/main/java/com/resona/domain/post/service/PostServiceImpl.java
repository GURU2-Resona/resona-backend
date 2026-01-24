package com.resona.domain.post.service;

import com.resona.domain.category.entity.Category;
import com.resona.domain.category.repository.CategoryRepository;
import com.resona.domain.member.entity.Member;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;
import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.PostCategory;
import com.resona.domain.post.entity.PostScene;
import com.resona.domain.post.entity.PostScrap;
import com.resona.domain.post.repository.PostCategoryRepository;
import com.resona.domain.post.repository.PostRepository;
import com.resona.domain.post.repository.PostSceneRepository;
import com.resona.domain.post.repository.PostScrapRepository;
import com.resona.domain.scene.entity.Scene;
import com.resona.domain.scene.repository.SceneRepository;
import com.resona.global.exception.GlobalException;
import com.resona.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final SceneRepository sceneRepository;
  private final PostCategoryRepository postCategoryRepository;
  private final PostSceneRepository postSceneRepository;
  private final PostScrapRepository postScrapRepository;

  @Override
  @Transactional
  public PostCreateResponse createPost(Long memberId, PostCreateRequest request) {
    // 작성자 조회
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

    // 게시글(Post) 생성 및 저장
    Post post =
        Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .songTitle(request.getSongTitle())
            .singer(request.getSinger())
            .songUrl(request.getSongUrl())
            .albumImage(request.getAlbumImage())
            .member(member)
            .saved(false)
            .build();

    Post savedPost = postRepository.save(post);

    // 카테고리 연결 (연결된 Category 객체를 반환받음)
    Category connectedCategory = connectCategory(savedPost, request.getCategory());

    // 상황 연결 (=연결된 Scene 객체를 반환받음)
    Scene connectedScene = connectScene(savedPost, request.getScene());

    // 결과 DTO 반환
    return PostCreateResponse.of(savedPost, connectedCategory, connectedScene);
  }

  private Category connectCategory(Post post, PostCreateRequest.TagRequest tagRequest) {
    if (tagRequest == null) return null;

    Category category = null;

    if (tagRequest.getId() != null) {
      category =
          categoryRepository
              .findById(tagRequest.getId())
              .orElseThrow(() -> new GlobalException(ErrorCode.CATEGORY_NOT_FOUND));
    } else if (tagRequest.getName() != null && !tagRequest.getName().isBlank()) {
      category =
          categoryRepository
              .findByName(tagRequest.getName())
              .orElseGet(
                  () ->
                      categoryRepository.save(
                          Category.builder().name(tagRequest.getName()).recommend(false).build()));
    }

    if (category != null) {
      postCategoryRepository.save(PostCategory.builder().post(post).category(category).build());
    }
    return category; // 찾거나 만든 객체 리턴
  }

  private Scene connectScene(Post post, PostCreateRequest.TagRequest tagRequest) {
    if (tagRequest == null) return null;

    Scene scene = null;

    if (tagRequest.getId() != null) {
      scene =
          sceneRepository
              .findById(tagRequest.getId())
              .orElseThrow(() -> new GlobalException(ErrorCode.SCENE_NOT_FOUND));
    } else if (tagRequest.getName() != null && !tagRequest.getName().isBlank()) {
      scene =
          sceneRepository
              .findByName(tagRequest.getName())
              .orElseGet(
                  () ->
                      sceneRepository.save(
                          Scene.builder().name(tagRequest.getName()).recommend(false).build()));
    }

    if (scene != null) {
      postSceneRepository.save(PostScene.builder().post(post).scene(scene).build());
    }
    return scene; // 찾거나 만든 객체 리턴
  }

   @Override
   @Transactional
   public boolean scrapPost(Long memberId, Long postId) {
        // 회원과 게시글 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        // 이미 스크랩되어 있는지 확인
        Optional<PostScrap> scrapOptional = postScrapRepository.findByMemberAndPost(member, post);

        if (scrapOptional.isPresent()) {
            // 이미 존재하면 -> 스크랩 취소 (삭제)
            postScrapRepository.delete(scrapOptional.get());
            return false;
        } else {
            //없으면 -> 스크랩 저장
            PostScrap postScrap = PostScrap.createScrap(member, post);
            postScrapRepository.save(postScrap);
            return true; // 저장됨
        }
    }
}
