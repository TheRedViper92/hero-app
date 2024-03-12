package pl.spartancode.heroapp.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.spartancode.heroapidto.hero.Hero;
import pl.spartancode.heroapp.hero.GetHeroService;
import pl.spartancode.heroapp.hero.MessageCreator;

@Route(value = "hero/search", layout = MainLayout.class)
@PageTitle("Hero Search")
public class HeroSearch extends VerticalLayout {
    private final GetHeroService getHeroService;
    private final MessageCreator messageCreator;

    private final TextField heroSearch;

    public HeroSearch(GetHeroService getHeroService, MessageCreator messageCreator) {
        this.getHeroService = getHeroService;
        this.messageCreator = messageCreator;

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        heroSearch = new TextField();
        heroSearch.setLabel("Hero search");
        heroSearch.setPlaceholder("Hero name");
        heroSearch.setClearButtonVisible(true);
        heroSearch.setRequired(true);
        horizontalLayout.add(heroSearch);

        Button heroSearchButton = new Button("Call Hero");
        heroSearchButton.addClickListener(this::getHeroSearchButtonClickListener);
        horizontalLayout.add(heroSearchButton);

        horizontalLayout.setVerticalComponentAlignment(Alignment.END, heroSearch, heroSearchButton);
        add(horizontalLayout);

        Button needAHeroButton = new Button("I need a hero!");
        needAHeroButton.addClickListener(this::getNeedAHeroButtonClickListener);
        add(needAHeroButton);
    }

    private void getNeedAHeroButtonClickListener(ClickEvent<Button> event) {
        Hero anyHero = getHeroService.getAnyHero();
        if (anyHero != null) {
            getHeroService.levelUpHero(anyHero);
        }
        String message = messageCreator.createMessage(anyHero);
        Notification notification = new Notification(message);
        notification.setPosition(Position.BOTTOM_END);
        notification.setDuration(3000);
        notification.addThemeName("success");
        notification.open();
    }

    private void getHeroSearchButtonClickListener(ClickEvent<Button> event) {
        if (heroSearch.isEmpty()) {
            return;
        }
        Notification notification = new Notification();
        String heroName = heroSearch.getValue();
        heroSearch.clear();
        Hero hero = getHeroService.getHero(heroName);

        if (hero != null) {
            getHeroService.levelUpHero(hero);
        }

        String message = messageCreator.createMessage(hero);
        notification.setText(message);
        notification.setPosition(Position.BOTTOM_END);
        notification.setDuration(3000);
        notification.addThemeName("success");
        notification.open();
    }
}
