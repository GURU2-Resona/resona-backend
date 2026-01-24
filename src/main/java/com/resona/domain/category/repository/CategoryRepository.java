package com.resona.domain.category.repository;

import com.resona.domain.category.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  // 직접 입력 시, 중복 확인을 위해 이름으로 조회
  Optional<Category> findByName(String name);
}
