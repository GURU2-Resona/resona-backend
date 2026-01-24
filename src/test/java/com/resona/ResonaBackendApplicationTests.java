package com.resona;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "kakao.client-id=dummy-kakao-id",
        "kakao.client-secret=dummy-kakao-secret",
        "openai.api-key=dummy-openai-key",
        "OPENAI_KEY=dummy-openai-key",
        "YOUTUBE_API_KEY=dummy-youtube-key",
        "jwt.secret=this-is-dummy-secret-key-for-test-1234567890",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password="
})
@ActiveProfiles("test")
class ResonaBackendApplicationTests {

  @Test
  void contextLoads() {}
}
