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
public class PostListResponse {

  private Long postId;
  private String title; // 추천글 제목
  private String songTitle; // 노래 제목
  private Long writerId;
  private String writerProfileImage; // 작성자 프로필 이미지
  private String writerNickname; // 작성자 닉네임
  private String categoryName;
  private String sceneName;

  public static PostListResponse of(Post post) {
    // 기타일 때 처리하는 로직
    String displayCategory =
        (post.getCategory() == Category.OTHER)
            ? post.getCustomCategory()
            : post.getCategory().getDescription();

    String displayScene =
        (post.getScene() == Scene.OTHER) ? post.getCustomScene() : post.getScene().getDescription();
    return PostListResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .songTitle(post.getSongTitle())
        .writerId(post.getMember().getId())
        .writerProfileImage(post.getMember().getProfileImage())
        .writerNickname(post.getMember().getNickname())
        .categoryName(displayCategory)
        .sceneName(displayScene)
        .build();
  }
}
