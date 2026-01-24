package com.resona.domain.post.entity;

import com.resona.domain.member.entity.Member;
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

  // 앨범 커버(썸네일) 이미지
  @Column(name = "album_image")
  private String albumImage;

  @Column(name = "saved")
  @Builder.Default
  private Boolean saved = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;
}
