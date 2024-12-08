package com.example.application.views.listazajęć;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Lista Zajęć")
@Route("lista-zajec")
@Menu(order = 0, icon = LineAwesomeIconUrl.LIST_SOLID)
@PermitAll
public class ListaZajęćView extends Composite<VerticalLayout> {

    public ListaZajęćView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        H5 h5 = new H5();
        H5 h52 = new H5();
        H5 h53 = new H5();
        H5 h54 = new H5();
        Paragraph textSmall = new Paragraph();
        Paragraph textSmall2 = new Paragraph();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Nazwa zajęć");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        h5.setText("Dzień zajęć");
        h5.setWidth("max-content");
        h52.setText("Kwota za miesiąc");
        h52.setWidth("max-content");
        h53.setText("Godzina zajęć");
        h53.setWidth("max-content");
        h54.setText("Prowadzący");
        h54.setWidth("max-content");
        textSmall.setText("Opis zajęć");
        textSmall.setWidth("100%");
        textSmall.getStyle().set("font-size", "var(--lumo-font-size-xs)");
        textSmall2.setText("Opis prowadzącego");
        textSmall2.setWidth("100%");
        textSmall2.getStyle().set("font-size", "var(--lumo-font-size-xs)");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Zapisz się");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Więcej informacji");
        buttonSecondary.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(h5);
        formLayout2Col.add(h52);
        formLayout2Col.add(h53);
        formLayout2Col.add(h54);
        formLayout2Col.add(textSmall);
        formLayout2Col.add(textSmall2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
    }
}
