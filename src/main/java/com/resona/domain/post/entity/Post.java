package com.resona.domain.post.entity;

import com.resona.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "saved")
    @Builder.Default // Builder 패턴 사용 시 기본값 false 적용
    private Boolean saved = false;

    // Member와 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 비즈니스 로직 (수정 편의 메서드 예시)
    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}