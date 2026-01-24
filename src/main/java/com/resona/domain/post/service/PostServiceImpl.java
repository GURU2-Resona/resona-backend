package com.resona.domain.post.service;

import com.resona.domain.member.entity.Member;
import com.resona.domain.member.repository.MemberRepository;
import com.resona.domain.post.dto.PostCreateRequest;
import com.resona.domain.post.dto.PostCreateResponse;
import com.resona.domain.post.entity.Post;
import com.resona.domain.post.entity.PostScrap;
import com.resona.domain.post.entity.enums.Category;
import com.resona.domain.post.entity.enums.Scene;
import com.resona.domain.post.repository.PostRepository;
import com.resona.domain.post.repository.PostScrapRepository;
import com.resona.global.exception.GlobalException;
import com.resona.global.response.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final PostScrapRepository postScrapRepository;

  @Override
  @Transactional
  public PostCreateResponse createPost(Long memberId, PostCreateRequest request) {
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

    Post post =
        Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .songTitle(request.getSongTitle())
            .singer(request.getSinger())
            .songUrl(request.getSongUrl())
            .albumImage(request.getAlbumImage())
            .member(member)
            .saved(false)
            .category(request.getCategory())
            .customCategory(
                request.getCategory() == Category.OTHER ? request.getCustomCategory() : null)
            .scene(request.getScene())
            .customScene(request.getScene() == Scene.OTHER ? request.getCustomScene() : null)
            .build();

    Post savedPost = postRepository.save(post);

    return PostCreateResponse.of(savedPost);
  }

  @Override
  @Transactional
  public boolean scrapPost(Long memberId, Long postId) {
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

    Optional<PostScrap> scrapOptional = postScrapRepository.findByMemberAndPost(member, post);

    if (scrapOptional.isPresent()) {
      postScrapRepository.delete(scrapOptional.get());
      return false;
    } else {
      PostScrap postScrap = PostScrap.createScrap(member, post);
      postScrapRepository.save(postScrap);
      return true;
    }
  }
}
