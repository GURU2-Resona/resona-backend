package com.resona.domain.post.service;

import com.resona.domain.category.entity.Category;
import com.resona.domain.category.repository.CategoryRepository;
import com.resona.domain.member.entity.Member;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.PostCategory;
import com.resona.domain.post.entity.PostScene;
import com.resona.domain.post.repository.PostCategoryRepository;
import com.resona.domain.post.repository.PostRepository;
import com.resona.domain.post.repository.PostSceneRepository;
import com.resona.domain.scene.entity.Scene;
import com.resona.domain.scene.repository.SceneRepository;
import com.resona.global.exception.GlobalException;
import com.resona.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Long createPost(Long memberId, PostCreateRequest request) {
        // 작성자 조회 (예외 처리 적용)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        // 게시글(Post) 생성 및 저장
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .songTitle(request.getSongTitle())
                .singer(request.getSinger())
                .songUrl(request.getSongUrl())
                .albumImage(request.getAlbumImage()) // DTO에서 받은 이미지 URL 저장
                .member(member)
                .saved(false)
                .build();

        Post savedPost = postRepository.save(post);

        // 카테고리 연결 (선택 or 생성)
        connectCategory(savedPost, request.getCategory());

        // 상황 연결 (선택 or 생성)
        connectScene(savedPost, request.getScene());

        return savedPost.getId();
    }

    /**
     * 카테고리 연결 로직
     */
    private void connectCategory(Post post, PostCreateRequest.TagRequest tagRequest) {
        if (tagRequest == null) return;

        Category category = null;

        // 기존 태그 ID가 있는 경우
        if (tagRequest.getId() != null) {
            category = categoryRepository.findById(tagRequest.getId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.CATEGORY_NOT_FOUND));
        }
        // 직접 입력(이름)이 있는 경우 -> 없으면 생성, 있으면 조회
        else if (tagRequest.getName() != null && !tagRequest.getName().isBlank()) {
            category = categoryRepository.findByName(tagRequest.getName())
                    .orElseGet(() -> categoryRepository.save(
                            Category.builder()
                                    .name(tagRequest.getName())
                                    .recommend(false) // 사용자가 만든 건 추천 목록 제외
                                    .build()
                    ));
        }

        if (category != null) {
            postCategoryRepository.save(PostCategory.builder()
                    .post(post)
                    .category(category)
                    .build());
        }
    }

    /**
     * 상황 연결 로직
     */
    private void connectScene(Post post, PostCreateRequest.TagRequest tagRequest) {
        if (tagRequest == null) return;

        Scene scene = null;

        if (tagRequest.getId() != null) {
            scene = sceneRepository.findById(tagRequest.getId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.SCENE_NOT_FOUND));
        } else if (tagRequest.getName() != null && !tagRequest.getName().isBlank()) {
            scene = sceneRepository.findByName(tagRequest.getName())
                    .orElseGet(() -> sceneRepository.save(
                            Scene.builder()
                                    .name(tagRequest.getName())
                                    .recommend(false)
                                    .build()
                    ));
        }

        if (scene != null) {
            postSceneRepository.save(PostScene.builder()
                    .post(post)
                    .scene(scene)
                    .build());
        }
    }
}