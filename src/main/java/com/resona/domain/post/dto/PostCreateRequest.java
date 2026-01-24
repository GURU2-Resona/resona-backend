package com.resona.domain.post.dto;


import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "노래 제목은 필수입니다.")
    private String songTitle;

    @NotBlank(message = "가수 이름은 필수입니다.")
    private String singer;

    @NotBlank(message = "노래 URL은 필수입니다.")
    private String songUrl;

    private String albumImage;

    @NotBlank(message = "글 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "카테고리는 필수입니다.")
    private Category category;

    private String customCategory; // category가 OTHER일 때 사용

    @NotNull(message = "상황은 필수입니다.")
    private Scene scene;

    private String customScene; // scene이 OTHER일 때 사용
}