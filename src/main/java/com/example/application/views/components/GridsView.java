package com.example.application.views.components;

import com.example.application.components.GridHeader;
import com.example.application.components.Item;
import com.example.application.components.Layout;
import com.example.application.themes.MenuBarTheme;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Grids")
@Route(value = "grids", layout = MainLayout.class)
public class GridsView extends ComponentView {

    private Grid<Employee> grid;
    private TextField searchField;

    String[] names = {
            "Alice Smith", "Bob Johnson", "Charlie Brown", "David Davis", "Eve Lee",
            "Frank Wilson", "Grace Clark", "Hannah Anderson", "Isaac Harris", "Julia Moore",
            "Kevin Taylor", "Linda Jackson", "Michael White", "Nancy Martin", "Oliver Thompson",
            "Peggy Walker", "Quincy Allen", "Rachel Young", "Samuel Wright", "Tina Lewis",
            "Ulysses Hall", "Victoria Hill", "Walter Adams", "Xena Carter", "Yasmine Robinson",
            "Zachary Turner", "Ava Parker", "Emily King", "Henry Hughes", "Sophia Scott",
            "Jackson Bennett", "Sophie Bell", "Noah Green", "Liam Reed", "Aria Carter",
            "Olivia Hall", "Ethan Adams", "Mia Turner", "Liam Brown", "Emma Wilson",
            "Jacob Harris", "Isabella Martin"
    };

    public GridsView() {
        addH2("Grid header");
        addPreview(createGridHeaderExample());
    }

    private Component createGridHeaderExample() {
        grid = new Grid();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(createEmployees());
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Employee::getFirstName).setHeader("First name");
        grid.addColumn(Employee::getLastName).setHeader("Last name");

        searchField = new TextField();
        searchField.setWidth("200px");
        searchField.addClassName(LumoUtility.Padding.NONE);
        searchField.setPlaceholder("Search");
        searchField.setClearButtonVisible(true);
        searchField.setSuffixComponent(LumoIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.setVisible(false);

        GridHeader header = new GridHeader("Employees", grid);
        header.setItemCount(names.length);
        header.setDefaultActions(createDefaultMenuBar(grid));
        header.setContextActions(createContextMenuBar());
        header.setGridActions(searchField, createGridMenuBar(grid));

        Layout layout = new Layout(header, grid);
        layout.addClassNames(LumoUtility.Background.BASE);
        layout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        return layout;
    }

    private MenuBar createGridMenuBar(Grid grid) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);

        MenuItem search = menuBar.addItem(LineAwesomeIcon.SEARCH_SOLID.create());
        search.setAriaLabel("Search");
        search.addClickListener(e -> {
            searchField.setVisible(true);
            searchField.focus();
            search.setVisible(false);
        });

        searchField.addBlurListener(e -> {
            search.setVisible(true);
            searchField.setVisible(false);
            if (!searchField.isEmpty()) {
                search.addClassName(LumoUtility.Background.PRIMARY_10);
            } else {
                search.removeClassName(LumoUtility.Background.PRIMARY_10);
            }
        });

        MenuItem filter = menuBar.addItem(LineAwesomeIcon.FILTER_SOLID.create());
        filter.setAriaLabel("Filter");
        MenuItem more = menuBar.addItem(LineAwesomeIcon.ELLIPSIS_V_SOLID.create());
        more.setAriaLabel("More");

        MenuItem columns = more.getSubMenu().addItem("Column visibility");
        columns.setAriaLabel("Columns");

        List<Grid.Column> cols = grid.getColumns();
        for (Grid.Column col : cols) {
            MenuItem menuItem = columns.getSubMenu().addItem(col.getHeaderText(), e -> col.setVisible(!col.isVisible()));
            menuItem.setCheckable(true);
            menuItem.setChecked(true);
            menuItem.setKeepOpen(true);
        }

        MenuItem compactMode = more.getSubMenu().addItem("Compact mode");
        compactMode.setCheckable(true);
        compactMode.addClickListener(e -> {
            if (grid.hasThemeName(GridVariant.LUMO_COMPACT.getVariantName())) {
                grid.removeThemeVariants(GridVariant.LUMO_COMPACT);
            } else {
                grid.addThemeVariants(GridVariant.LUMO_COMPACT);
            }
        });

        more.getSubMenu().addItem("Export to...");
        more.getSubMenu().add(new Hr());
        more.getSubMenu().addItem("Reset to defaults");

        return menuBar;
    }

    private MenuBar createDefaultMenuBar(Grid grid) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeNames(MenuBarTheme.ROUNDED, MenuBarTheme.GAP_MEDIUM);
        menuBar.addClassNames(LumoUtility.Border.RIGHT, LumoUtility.BorderColor.CONTRAST_20, LumoUtility.Padding.Right.SMALL);

        MenuItem add = menuBar.addItem(new Item("New employee", LineAwesomeIcon.PLUS_SOLID));
        add.addThemeNames(MenuBarVariant.LUMO_PRIMARY.getVariantName());

        return menuBar;
    }

    private MenuBar createContextMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeNames(MenuBarTheme.ROUNDED, MenuBarTheme.GAP_MEDIUM);
        menuBar.addClassNames(LumoUtility.Border.RIGHT, LumoUtility.BorderColor.CONTRAST_20, LumoUtility.Padding.Right.SMALL);

        menuBar.addItem(new Item("Delete", LineAwesomeIcon.TRASH_SOLID));

        MenuItem more = menuBar.addItem(LineAwesomeIcon.ELLIPSIS_V_SOLID.create());
        more.setAriaLabel("More");
        more.addThemeNames(MenuBarVariant.LUMO_ICON.getVariantName());

        more.getSubMenu().addItem(new Item("Favourite", LineAwesomeIcon.HEART));
        more.getSubMenu().addItem(new Item("Label", LineAwesomeIcon.TAG_SOLID));
        more.getSubMenu().addItem(new Item("Print", LineAwesomeIcon.PRINT_SOLID));
        more.getSubMenu().addItem(new Item("Export", LineAwesomeIcon.FILE_EXPORT_SOLID));

        return menuBar;
    }

    private ArrayList<Employee> createEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (String name : names) {
            employees.add(new Employee(name.split(" ")[0], name.split(" ")[1]));
        }
        return employees;
    }


    private class Employee {
        private String firstName;
        private String lastName;

        public Employee(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
