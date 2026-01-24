package com.resona.domain.post.repository;

import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
  // 동적 필터링 쿼리
  // 게시글 가져올 때 작성자 정보도 같이 가져옴
  // 파라미터가 없으면 전체 조회, 있으면 조건 검색
  @Query(
      "SELECT p FROM Post p JOIN FETCH p.member m "
          + "WHERE (:category IS NULL OR p.category = :category) "
          + "AND (:scene IS NULL OR p.scene = :scene) "
          + "ORDER BY p.id DESC")
  List<Post> findAllByFilters(@Param("category") Category category, @Param("scene") Scene scene);

  // 상세 조회용 -> ID로 조회하되 작성자 정보까지 한번에 가져옴
  @Query("SELECT p FROM Post p JOIN FETCH p.member WHERE p.id = :id")
  Optional<Post> findByIdWithMember(@Param("id") Long id);
}
