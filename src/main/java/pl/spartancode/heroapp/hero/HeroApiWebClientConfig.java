package pl.spartancode.heroapp.hero;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HeroApiWebClientConfig {
    @Value("${hero.api.url}")
    private String heroApiUrl;

    @Bean
    public WebClient heroApiWebClient() {
        return WebClient.builder()
            .baseUrl(heroApiUrl)
            .build();
    }
}