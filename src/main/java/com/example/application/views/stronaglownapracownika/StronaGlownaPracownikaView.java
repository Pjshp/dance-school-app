package com.example.application.views.stronaglownapracownika;

import com.example.application.data.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Strona Główna Pracownika")
@Route("strona-glowna-pracownika")
@Menu(order = 2, icon = LineAwesomeIconUrl.HOME_SOLID)
@PermitAll
@Uses(Icon.class)
public class StronaGlownaPracownikaView extends Composite<VerticalLayout> {

    public StronaGlownaPracownikaView() {
        Tabs tabs = new Tabs();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        H5 h5 = new H5();
        H5 h52 = new H5();
        H5 h53 = new H5();
        Grid multiSelectGrid = new Grid(User.class);
        HorizontalLayout layoutRow = new HorizontalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        Button buttonTertiary = new Button();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H3 h32 = new H3();
        TextField textField = new TextField();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        tabs.setWidth("100%");
        setTabsSampleData(tabs);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Nazwa zajęć");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        h5.setText("Prowadzący");
        h5.setWidth("max-content");
        h52.setText("Kwota za miesiąc");
        h52.setWidth("max-content");
        h53.setText("Ilość osób w grupie: x");
        h53.setWidth("max-content");
        multiSelectGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        multiSelectGrid.setWidth("100%");
        multiSelectGrid.getStyle().set("flex-grow", "0");
        setGridSampleData(multiSelectGrid);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Pokaż skład grupy");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Edytuj należność uczestnika");
        buttonSecondary.setWidth("min-content");
        buttonTertiary.setText("Usuń uczestnika z grupy");
        buttonTertiary.setWidth("min-content");
        buttonTertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        layoutColumn3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.setMaxWidth("800px");
        layoutColumn3.getStyle().set("flex-grow", "1");
        h32.setText("Edycja należności uczestnika");
        h32.setWidth("max-content");
        textField.setLabel("Wprowadź nową należność");
        textField.setWidth("min-content");
        getContent().add(tabs);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(h5);
        formLayout2Col.add(h52);
        formLayout2Col.add(h53);
        layoutColumn2.add(multiSelectGrid);
        layoutColumn2.add(layoutRow);
        layoutRow.add(layoutRow2);
        layoutRow2.add(buttonPrimary);
        layoutRow2.add(buttonSecondary);
        layoutRow2.add(buttonTertiary);
        getContent().add(layoutColumn3);
        layoutColumn3.add(h32);
        layoutColumn3.add(textField);
    }

    private void setTabsSampleData(Tabs tabs) {
        tabs.add(new Tab("Dashboard"));
        tabs.add(new Tab("Payment"));
        tabs.add(new Tab("Shipping"));
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> userService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private UserService userService;
}
