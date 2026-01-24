package com.resona.domain.post.dto;

import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
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
  private String categoryName;
  private String sceneName;

  public static PostCreateResponse of(Post post) {
    // 카테고리가 기타(OTHER)면 사용자가 입력한 값을, 아니면 Enum의 한글 설명을 반환
    String displayCategory =
        (post.getCategory() == Category.OTHER)
            ? post.getCustomCategory()
            : post.getCategory().getDescription();

    String displayScene =
        (post.getScene() == Scene.OTHER) ? post.getCustomScene() : post.getScene().getDescription();

    return PostCreateResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .songTitle(post.getSongTitle())
        .singer(post.getSinger())
        .songUrl(post.getSongUrl())
        .albumImage(post.getAlbumImage())
        .saved(post.getSaved())
        .categoryName(displayCategory)
        .sceneName(displayScene)
        .build();
  }
}
