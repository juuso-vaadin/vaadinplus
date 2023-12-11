package com.example.application.components;

import com.example.application.utilities.FontSize;
import com.example.application.utilities.FontWeight;
import com.example.application.utilities.Gap;
import com.example.application.utilities.HeadingLevel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class GridHeader extends Header {

    private String title;
    private Component[] defaultActions;
    private Component[] contextActions;
    private Component[] gridActions;

    private Integer itemCount;

    private Span items;
    private Grid grid;

    public GridHeader(String title) {
        this(title, HeadingLevel.H2);
        setHeadingFontSize(FontSize.LARGE);
    }

    public GridHeader(String title, Grid grid) {
        this(title, HeadingLevel.H2);
        setGrid(grid);
    }

    public GridHeader(String title, HeadingLevel level) {
        super(title, level);
        this.title = title;
        setHeadingFontSize(FontSize.LARGE);
    }

    public GridHeader(String title, HeadingLevel level, Grid grid) {
        this(title, level);
        setGrid(grid);
    }

    public void setGrid(Grid grid) {
        grid.addSelectionListener(e -> {
            int size = e.getAllSelectedItems().size();
            updateActionsVisibility(size);
        });
        this.grid = grid;
    }

    public void setDefaultActions(Component... components) {
        this.defaultActions = components;
        updateActions();
    }

    private void setDefaultActionsVisible(boolean visible) {
        if (this.defaultActions != null)
            for (Component defaultAction : this.defaultActions) {
                defaultAction.setVisible(visible);
            }
    }

    public void setContextActions(Component... components) {
        this.contextActions = components;
        updateActions();
    }

    private void setContextActionsVisible(boolean visible) {
        if (this.contextActions != null)
            for (Component contextAction : this.contextActions) {
                contextAction.setVisible(visible);
            }
    }

    public void setGridActions(Component... components) {
        this.gridActions = components;
        this.actions.addClassNames(LumoUtility.AlignItems.CENTER);
        updateActions();
    }

    private void setGridActionsVisible(boolean visible) {
        if (this.gridActions != null)
            for (Component gridAction : this.gridActions) {
                gridAction.setVisible(visible);
            }
    }


    private void updateActions() {
        this.actions.removeAll();
        addActions(this.defaultActions);
        addActions(this.contextActions);
        addActions(this.gridActions);
        updateActionsVisibility(grid.getSelectedItems().size());
    }

    private void updateActionsVisibility(int size) {
        if (size == 0) {
            setHeading(this.title);
            setHeadingFontSize(FontSize.LARGE);
            setHeadingFontWeight(FontWeight.SEMIBOLD);
            removeClassNames(LumoUtility.Background.PRIMARY_10);
            setDefaultActionsVisible(true);
            setContextActionsVisible(false);
            setItemCountVisible(true);
        } else {
            setHeading(size + " items selected");
            setHeadingFontSize(FontSize.MEDIUM);
            setHeadingFontWeight(FontWeight.NORMAL);
            addClassNames(LumoUtility.Background.PRIMARY_10);
            setDefaultActionsVisible(false);
            setContextActionsVisible(true);
            setItemCountVisible(false);
        }
    }

    public void setItemCount(Integer itemCount) {
        items = new Span();
        items.addClassName(LumoUtility.TextColor.SECONDARY);
        if (itemCount != null) {
            items.setText(itemCount.toString());
        }
        this.setDetails(items);
        this.getColumnLayout().setFlexDirection(FlexDirection.ROW);
        this.getColumnLayout().addClassName(LumoUtility.AlignItems.BASELINE);
    }

    public void setItemCountVisible(Boolean visible) {
        if (visible) {
            items.setVisible(true);
        } else {
            items.setVisible(false);
        }
    }

}
