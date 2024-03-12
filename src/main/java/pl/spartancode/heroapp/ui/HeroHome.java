package pl.spartancode.heroapp.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/", layout = MainLayout.class)
@PageTitle("Hero Home")
public class HeroHome extends VerticalLayout {
    public HeroHome() {
        addClassName("home-view");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        add(
            new H2("Welcome to our Hero Service!"),
            new Paragraph("Where every hero is a HERO!")
        );
    }
}
