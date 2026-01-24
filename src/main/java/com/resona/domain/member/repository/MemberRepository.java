package com.resona.domain.member.repository;

import com.resona.domain.member.entity.Member;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    @NonNull
    Optional<Member> findById(Long id);
    Optional<Member> findByProviderId(String kakaoId);
}
