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
        // 1. 작성자 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + memberId));

        // 2. 게시글(Post) 생성 및 저장
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .songTitle(request.getSongTitle())
                .singer(request.getSinger())
                .songUrl(request.getSongUrl())
                // .albumImage(request.getAlbumImage()) // 엔티티에 추가 시 주석 해제
                .member(member)
                .saved(false)
                .build();

        Post savedPost = postRepository.save(post);

        // 3. 카테고리 연결 (선택 or 생성)
        connectCategory(savedPost, request.getCategory());

        // 4. 상황 연결 (선택 or 생성)
        connectScene(savedPost, request.getScene());

        return savedPost.getId();
    }

    /**
     * 카테고리 연결 로직
     */
    private void connectCategory(Post post, PostCreateRequest.TagRequest tagRequest) {
        if (tagRequest == null) return;

        Category category = null;

        // 1) 기존 태그 ID가 있는 경우
        if (tagRequest.getId() != null) {
            category = categoryRepository.findById(tagRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        }
        // 2) 직접 입력(이름)이 있는 경우 -> 없으면 생성, 있으면 조회
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
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상황 태그입니다."));
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