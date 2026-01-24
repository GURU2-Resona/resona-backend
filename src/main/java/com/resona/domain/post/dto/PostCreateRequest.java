package com.resona.domain.post.dto;

import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import jakarta.validation.constraints.AssertTrue; // 추가
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

    private String customCategory;

    @NotNull(message = "상황은 필수입니다.")
    private Scene scene;

    private String customScene;

    @AssertTrue(message = "카테고리가 '기타'인 경우 내용을 입력해야 하며, 그 외에는 비워야 합니다.")
    private boolean isCategoryCustomValid() {
        if (category == null) return true; // @NotNull에서 이미 처리됨

        boolean hasCustom = customCategory != null && !customCategory.isBlank();

        if (category == Category.OTHER) {
            return hasCustom; // OTHER면 값이 있어야 함 (true여야 통과)
        } else {
            return !hasCustom; // OTHER가 아니면 값이 없어야 함 (false여야 통과 -> !false = true)
        }
    }

    @AssertTrue(message = "상황이 '기타'인 경우 내용을 입력해야 하며, 그 외에는 비워야 합니다.")
    private boolean isSceneCustomValid() {
        if (scene == null) return true;

        boolean hasCustom = customScene != null && !customScene.isBlank();

        if (scene == Scene.OTHER) {
            return hasCustom;
        } else {
            return !hasCustom;
        }
    }
}