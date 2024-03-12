package pl.spartancode.heroapp.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final DrawerConfig drawerConfig;

    public MainLayout(DrawerConfig drawerConfig) {
        this.drawerConfig = drawerConfig;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Hero Service");
        logo.addClassNames("text-l", "m-m");

        DrawerToggle toggle = new DrawerToggle();

        HorizontalLayout header = new HorizontalLayout(toggle, logo);
        header.setVerticalComponentAlignment(Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink[] routerLinks = drawerConfig.getRouterLinks();
        addToDrawer(new VerticalLayout(routerLinks));
    }
}
