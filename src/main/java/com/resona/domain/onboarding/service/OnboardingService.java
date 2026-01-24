package com.resona.domain.onboarding.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resona.domain.onboarding.dto.OnboardingReqDto;
import com.resona.domain.onboarding.dto.OnboardingResDto;
import com.resona.domain.onboarding.dto.YoutubeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final GptService gptService;
    private final RestTemplate restTemplate;
    @Value("${YOUTUBE_API_KEY}")
    public String youtubeApiKey;
    public static final int MAX_RETRY = 3;

    public OnboardingResDto.Recommend recommendMusic(String category, String scene) {

        for (int attempt = 1; attempt <= MAX_RETRY; attempt++) {

            // 1. GPT 추천
            OnboardingResDto.Song song =
                    gptService.recommendFromGpt(category, scene);

            // 2️. YouTube 존재 검증
            String youtubeUrl = searchYoutube(song.getTitle(), song.getArtist());

            // 3️. 검색 성공 시 바로 반환
            if (youtubeUrl != null) {
                return OnboardingResDto.Recommend.builder()
                        .title(song.getTitle())
                        .artist(song.getArtist())
                        .youtubeUrl(youtubeUrl)
                        .build();
            }

            // 실패 시
            log.warn("GPT 추천 실패 ({}회차): {} - {}",
                    attempt, song.getArtist(), song.getTitle());
        }

        // 3회 실패 시
        return OnboardingResDto.Recommend.builder()
                .title("")
                .artist("")
                .youtubeUrl("적합한 링크를 찾지 못했습니다.")
                .build();
    }


    public String searchYoutube(String title, String artist) {
        String query = artist + " " + title + " official";

        String url = "https://www.googleapis.com/youtube/v3/search"
                + "?part=snippet"
                + "&q=" + query
                + "&type=video"
                + "&maxResults=5"
                + "&key=" + youtubeApiKey;

        YoutubeResponse res =
                restTemplate.getForObject(url, YoutubeResponse.class);

        if (res == null || res.getItems() == null || res.getItems().isEmpty()) {
            return null;
        }

        return pickBestVideo(res, title, artist);
    }

    private String pickBestVideo(
            YoutubeResponse res, String title, String artist) {
        String lowerTitle = title.toLowerCase().replaceAll(" ", "");
        String lowerArtist = artist.toLowerCase().replaceAll(" ", "");
        System.out.println("가수: " + artist + " 제목: " + title);

        return res.getItems().stream()
                .filter(item -> {
                    String videoTitle = item.getSnippet().getTitle().toLowerCase().replaceAll(" ", "");
                    System.out.println("추천 제목: " + videoTitle);
                    boolean hasBasicInfo = videoTitle.contains(lowerTitle) && videoTitle.contains(lowerArtist);
                    boolean isNotNoise = !videoTitle.contains("ai") &&
                            !videoTitle.contains("cover") &&
                            !videoTitle.contains("노래방") &&
                            !videoTitle.contains("playlist");
                    return videoTitle.contains(lowerTitle) && videoTitle.contains(lowerArtist);
                })
                .findFirst()
                .map(item ->
                        "https://www.youtube.com/watch?v="
                                + item.getId().getVideoId()
                )
                .orElse(null);
    }
}
