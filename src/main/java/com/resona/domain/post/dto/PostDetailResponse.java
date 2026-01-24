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
public class PostDetailResponse {

  private Long postId;

  // 작성자 정보
  private String writerProfileImage;
  private String writerNickname;
  private Long writerId;

  // 노래 및 글 정보
  private String title; // 추천글 제목
  private String content; // 추천글 내용
  private String songTitle; // 노래 제목
  private String singer; // 가수
  private String songUrl; // 노래 링크 (필요시 사용)
  private String albumImage; // 앨범 커버

  // 상태 플래그
  private Boolean isSaved; // 내가 저장했는지 여부
  private Boolean isMine; // 내가 쓴 글인지 여부 (수정/삭제 버튼용)

  // 해시태그 (카테고리/상황)
  private String categoryName;
  private String sceneName;

  public static PostDetailResponse of(Post post, boolean isSaved, boolean isMine) {
    // 카테고리 처리: OTHER면 customCategory, 아니면 Enum 설명
    String displayCategory =
        (post.getCategory() == Category.OTHER)
            ? post.getCustomCategory()
            : post.getCategory().getDescription();

    // 상황 처리: OTHER면 customScene, 아니면 Enum 설명
    String displayScene =
        (post.getScene() == Scene.OTHER) ? post.getCustomScene() : post.getScene().getDescription();

    return PostDetailResponse.builder()
        .postId(post.getId())
        .writerProfileImage(post.getMember().getProfileImage())
        .writerNickname(post.getMember().getNickname())
        .writerId(post.getMember().getId())
        .title(post.getTitle())
        .content(post.getContent())
        .songTitle(post.getSongTitle())
        .singer(post.getSinger())
        .songUrl(post.getSongUrl())
        .albumImage(post.getAlbumImage())
        .isSaved(isSaved)
        .isMine(isMine)
        .categoryName(displayCategory)
        .sceneName(displayScene)
        .build();
  }
}
