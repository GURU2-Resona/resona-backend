package com.resona.domain.post.entity;

import com.resona.domain.member.entity.Member;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import com.resona.global.exception.GlobalException;
import com.resona.global.response.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "song_title", nullable = false)
  private String songTitle;

  @Column(name = "singer", nullable = false, length = 50)
  private String singer;

  @Column(name = "song_url", nullable = false)
  private String songUrl;

  @Column(name = "album_image")
  private String albumImage;

  @Column(name = "saved")
  @Builder.Default
  private Boolean saved = false;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @Column(name = "custom_category")
  private String customCategory;

  @Enumerated(EnumType.STRING)
  @Column(name = "scene", nullable = false)
  private Scene scene;

  @Column(name = "custom_scene")
  private String customScene;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  // DB 저장/수정 전 데이터 무결성 체크 ---
  @PrePersist
  @PreUpdate
  public void validateCustomFields() {
    // Category 검증
    if (this.category == Category.OTHER) {
      if (this.customCategory == null || this.customCategory.isBlank()) {
        throw new GlobalException(ErrorCode.CATEGORY_OTHER_ESSENTIAL);
      }
    } else {
      this.customCategory = null; // 기타가 아니면 강제로 null 처리
    }

    // Scene 검증
    if (this.scene == Scene.OTHER) {
      if (this.customScene == null || this.customScene.isBlank()) {
        throw new GlobalException(ErrorCode.SCENE_OTHER_ESSENTIAL);
      }
    } else {
      this.customScene = null; // 기타가 아니면 강제로 null 처리
    }
  }
}
