package pl.spartancode.heroapp.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Horizontal;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Vertical;

@CssImport("./styles/styles.css")
public class MainLayout extends AppLayout {
    private final DrawerConfig drawerConfig;

    public MainLayout(DrawerConfig drawerConfig) {
        this.drawerConfig = drawerConfig;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 appTitle = new H1("Hero Service");
        appTitle.addClassNames(
            FontSize.LARGE,
            Margin.MEDIUM);

        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("drawer-toggle");
        toggle.setIcon(new Icon("lumo", "menu"));

        HorizontalLayout header = new HorizontalLayout(toggle, appTitle);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(Vertical.NONE, Horizontal.MEDIUM);
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink[] routerLinks = drawerConfig.getRouterLinks();
        addToDrawer(new VerticalLayout(routerLinks));
        setDrawerOpened(false);
    }
}
