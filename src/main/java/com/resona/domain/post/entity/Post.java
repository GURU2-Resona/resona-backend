package com.resona.domain.post.entity;

import com.resona.domain.member.entity.Member;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
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
}
