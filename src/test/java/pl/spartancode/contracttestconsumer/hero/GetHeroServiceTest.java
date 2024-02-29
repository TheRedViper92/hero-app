package pl.spartancode.contracttestconsumer.hero;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import pl.spartancode.contracttestconsumer.hero.HeroDto.Hero;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class GetHeroServiceTest {
    @Mock
    private WebClient heroWebClientApi;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    private GetHeroService getHeroService;

    @BeforeEach
    void setUp() {
        getHeroService = new GetHeroService(heroWebClientApi);
    }

    @Test
    void getAnyHero() {
        //given
        when(heroWebClientApi.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/api/v1/hero/any")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Hero testHero = new Hero("Terminator", "0");
        when(responseSpec.bodyToMono(Hero.class)).thenReturn(Mono.just(testHero));

        //when
        Hero anyHero = getHeroService.getAnyHero();
        //then
        Assertions.assertThat(anyHero).isEqualTo(testHero);
    }

    @Test
    void getHero() {
        //given
        when(heroWebClientApi.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/api/v1/hero/Terminator")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Hero testHero = new Hero("Terminator", "0");
        when(responseSpec.bodyToMono(Hero.class)).thenReturn(Mono.just(testHero));
        //when
        Hero anyHero = getHeroService.getHero("Terminator");
        //then
        Assertions.assertThat(anyHero).isEqualTo(testHero);
    }

    @Test
    void noHeroAvailable() {
        //given
        when(heroWebClientApi.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/api/v1/hero/any")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Hero.class)).thenReturn(Mono.empty());
        //when
        Hero anyHero = getHeroService.getAnyHero();
        //then
        Assertions.assertThat(anyHero).isNull();
    }
}