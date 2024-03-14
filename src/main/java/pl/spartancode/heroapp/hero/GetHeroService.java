package pl.spartancode.heroapp.hero;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import pl.spartancode.heroapidto.hero.Hero;
import reactor.core.publisher.Mono;

@Service
public class GetHeroService {
    private static final Logger log = LoggerFactory.getLogger(GetHeroService.class);
    private final WebClient heroApiWebClient;

    public GetHeroService(WebClient heroApiWebClient) {
        this.heroApiWebClient = heroApiWebClient;
    }

    public Hero getHero(String heroName) {
        return heroApiWebClient
            .get()
            .uri("/api/v1/hero/name/" + heroName)
            .retrieve()
            .bodyToMono(Hero.class)
            .doOnError(WebClientRequestException.class, throwable -> log.error(
                "Error while getting hero \"{}\". Could not reach endpoint: [{}] {}. ",
                heroName, throwable.getMethod(), throwable.getUri()))
            .block();
    }

    public List<Hero> getAllHeroes() {
        return heroApiWebClient
            .get()
            .uri("/api/v1/hero/all")
            .retrieve()
            .bodyToFlux(Hero.class)
            .doOnError(WebClientRequestException.class, throwable -> log.error(
                "Error while getting all hero. Could not reach endpoint: [{}] {}. ",
                throwable.getMethod(), throwable.getUri()))
            .collectList()
            .block();
    }

    public void levelUpHero(Hero hero) {
        log.info("sending request to level up hero: " + hero.getName());
        heroApiWebClient
            .post()
            .uri("/api/v1/hero/lvup")
            .header("Content-Type", "text/plain")
            .body(BodyInserters.fromValue(hero.getName()))
            .exchangeToMono(clientResponse -> {
                if (clientResponse.statusCode().is2xxSuccessful()) {
                    System.out.println("Hero " + hero.getName() + " has been levelled up");
                    return clientResponse.bodyToMono(Void.class);
                } else {
                    System.out.println("Error while levelling up hero");
                    return Mono.error(new RuntimeException(
                        "Error while levelling up hero, " + clientResponse.statusCode()));
                }

            })
            .doFinally(signalType -> log.info(
                "Request to level up hero: " + hero.getName() + " has been sent"))
            .subscribe();
    }
}
