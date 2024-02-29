package pl.spartancode.contracttestconsumer.hero;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pl.spartancode.contracttestconsumer.hero.HeroDto.Hero;

@Service
public class GetHeroService {
    private static final Logger log = LoggerFactory.getLogger(GetHeroService.class);
    private WebClient heroApiWebClient;

    public GetHeroService(WebClient heroApiWebClient) {
        this.heroApiWebClient = heroApiWebClient;
    }

    public Hero getAnyHero() {
        return heroApiWebClient.get()
            .uri("/api/v1/hero/any")
            .retrieve()
            .bodyToMono(Hero.class)
            .block();
    }

    public Hero getHero(String heroName) {
        return heroApiWebClient
            .get()
            .uri("/api/v1/hero/" + heroName)
            .retrieve()
            .bodyToMono(Hero.class)
            .block();
    }

    public void levelUpHero(Hero hero) {
        log.info("sending request to level up hero: " + hero.name());
        heroApiWebClient
            .post()
            .uri(URI.create("/api/v1/hero/lvup"))
            .body(BodyInserters.fromValue(hero.name()))
            .retrieve();
    }
}
