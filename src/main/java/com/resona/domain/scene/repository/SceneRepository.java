package com.resona.domain.scene.repository;

import com.resona.domain.scene.entity.Scene;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SceneRepository extends JpaRepository<Scene, Long> {
  Optional<Scene> findByName(String name);
}
