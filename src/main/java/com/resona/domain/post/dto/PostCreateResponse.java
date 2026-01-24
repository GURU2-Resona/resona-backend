package com.resona.domain.post.dto;

import com.resona.domain.category.entity.Category;
import com.resona.domain.post.entity.Post;
import com.resona.domain.scene.entity.Scene;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCreateResponse {

    private Long postId;
    private String title;
    private String content;
    private String songTitle;
    private String singer;
    private String songUrl;
    private String albumImage;
    private Boolean saved;

    // 카테고리와 상황은 객체로 내려주기 (id, name 포함)
    private TagDto category;
    private TagDto scene;

    // 내부 클래스로 태그 정보 정의
    @Getter
    @Builder
    @AllArgsConstructor
    public static class TagDto {
        private Long id;
        private String name;
    }

    // Entity들을 받아서 Response로 변환해주는 메서드
    public static PostCreateResponse of(Post post, Category category, Scene scene) {
        return PostCreateResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .songTitle(post.getSongTitle())
                .singer(post.getSinger())
                .songUrl(post.getSongUrl())
                .albumImage(post.getAlbumImage())
                .saved(post.getSaved())
                .category(category != null ? new TagDto(category.getId(), category.getName()) : null)
                .scene(scene != null ? new TagDto(scene.getId(), scene.getName()) : null)
                .build();
    }
}