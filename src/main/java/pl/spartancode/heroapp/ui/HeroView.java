package pl.spartancode.heroapp.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "hero/id", layout = MainLayout.class)
public class HeroView extends VerticalLayout implements HasUrlParameter<String> {
    private String heroId;
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        add(new Text("Hero ID: " + parameter));
    }
}
