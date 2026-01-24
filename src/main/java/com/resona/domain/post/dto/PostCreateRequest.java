package com.resona.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    // 노래 정보 (프론트에서 검색해서 채워준 값)
    @NotBlank(message = "노래 제목은 필수입니다.")
    private String songTitle;

    @NotBlank(message = "가수 이름은 필수입니다.")
    private String singer;

    @NotBlank(message = "노래 URL은 필수입니다.")
    private String songUrl;

    private String albumImage; // 썸네일 URL

    // 게시글 정보
    @NotBlank(message = "글 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    // 카테고리 & 상황 (선택 or 직접입력)
    private TagRequest category;
    private TagRequest scene;

    @Getter
    @NoArgsConstructor
    public static class TagRequest {
        private Long id;       // 기존 태그 선택 시 ID 값
        private String name;   // 직접 작성하기 시 입력한 텍스트
    }
}