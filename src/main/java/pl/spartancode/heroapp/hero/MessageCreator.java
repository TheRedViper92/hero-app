package pl.spartancode.heroapp.hero;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import pl.spartancode.heroapidto.hero.Hero;

@Service
public class MessageCreator {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MessageCreator.class);

    public String createMessage(String heroJson) {
        Hero hero = jsonToHero(heroJson);
        return createMessage(hero);
    }

    public String createMessage(Hero hero) {
        if (hero == null) {
            return "No more heroes :(";
        }

        return String.format("Hero %s, on lv. %s joins ze battle", hero.getName(), hero.getLevel());
    }

    private Hero jsonToHero(String heroJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(heroJson, Hero.class);
        } catch (JsonProcessingException e) {
            log.error("Could not create Hero from JSON", e);
            return null;
        }
    }
}