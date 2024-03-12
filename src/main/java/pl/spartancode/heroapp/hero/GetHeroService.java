package pl.spartancode.heroapp.hero;

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
    private WebClient heroApiWebClient;

    public GetHeroService(WebClient heroApiWebClient) {
        this.heroApiWebClient = heroApiWebClient;
    }

    public Hero getAnyHero() {
        return heroApiWebClient.get()
            .uri("/api/v1/hero/any")
            .retrieve()
            .bodyToMono(Hero.class)
            .doOnError(WebClientRequestException.class, throwable -> log.error(
                "Error while getting any hero. Could not reach endpoint: [{}] {}. ",
                throwable.getMethod(), throwable.getUri()))
            .onErrorResume(WebClientRequestException.class, throwable -> Mono.empty())
            .block();
    }

    public Hero getHero(String heroName) {
        return heroApiWebClient
            .get()
            .uri("/api/v1/hero/" + heroName)
            .retrieve()
            .bodyToMono(Hero.class)
            .doOnError(WebClientRequestException.class, throwable -> log.error(
                "Error while getting any hero. Could not reach endpoint: [{}] {}. ",
                throwable.getMethod(), throwable.getUri()))
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
