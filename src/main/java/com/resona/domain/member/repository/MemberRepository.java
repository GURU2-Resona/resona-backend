package com.resona.domain.member.repository;

import com.resona.domain.member.entity.Member;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  @Override
  @NonNull
  Optional<Member> findById(Long id);

  Optional<Member> findByProviderId(String kakaoId);
}
