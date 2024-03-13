package pl.spartancode.heroapp.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import pl.spartancode.heroapidto.hero.Hero;
import pl.spartancode.heroapp.hero.GetHeroService;

@Route(value = "hero/browse", layout = MainLayout.class)
@PageTitle("Hero Browser")
public class HeroBrowser extends VerticalLayout {
    private final GetHeroService getHeroService;

    public HeroBrowser(GetHeroService getHeroService) {
        this.getHeroService = getHeroService;
        setSizeFull();
        Grid<Hero> grid = createGrid();
        updateGridData(grid);
        add(grid);
    }

    private Grid<Hero> createGrid() {
        Grid<Hero> grid = new Grid<>(Hero.class);
        grid.addClassNames("hero-grid");
        grid.removeAllColumns();
        grid.setHeightFull();
        grid.addColumn(Hero::getName).setHeader("Hero Name").setSortable(true);
        grid.addColumn(Hero::getLevel).setHeader("Level").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        return grid;
    }

    private void updateGridData(Grid<Hero> gridToUpdate) {
        List<Hero> provider = getHeroService.getAllHeroes();
        gridToUpdate.setItems(provider);
    }
}
