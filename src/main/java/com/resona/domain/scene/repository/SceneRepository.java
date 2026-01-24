package com.resona.domain.scene.repository;

import com.resona.domain.scene.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SceneRepository extends JpaRepository<Scene, Long> {
    Optional<Scene> findByName(String name);
}