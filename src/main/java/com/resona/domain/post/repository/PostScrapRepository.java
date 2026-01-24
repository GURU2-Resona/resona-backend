package com.resona.domain.post.repository;

import com.resona.domain.member.entity.Member;
import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.PostScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {
    // 해당 멤버가 해당 게시글을 저장했는지 확인
    Optional<PostScrap> findByMemberAndPost(Member member, Post post);
    //존재 여부 확인
    boolean existsByMemberAndPost(Member member, Post post);
}