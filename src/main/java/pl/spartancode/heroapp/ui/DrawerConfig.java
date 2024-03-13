package pl.spartancode.heroapp.ui;

import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import java.util.Arrays;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DrawerConfig {
    public RouterLink[] getRouterLinks() {
        RouterLink[] links = new RouterLink[]{
            new RouterLink("Home", HeroHome.class),
            new RouterLink("Search", HeroSearch.class),
            new RouterLink("Browser", HeroBrowser.class)
        };
        Arrays.stream(links)
            .forEach(link -> link.setHighlightCondition(HighlightConditions.sameLocation()));
        return links;
    }
}
