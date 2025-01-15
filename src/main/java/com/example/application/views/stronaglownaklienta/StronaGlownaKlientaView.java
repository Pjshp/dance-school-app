package com.example.application.views.stronaglownaklienta;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
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

@PageTitle("Strona Główna Klienta")
@Route(value = "strona-glowna-klienta")
@Menu(order = 4, icon = LineAwesomeIconUrl.HOME_SOLID)
@PermitAll
public class StronaGlownaKlientaView extends Composite<VerticalLayout> {

    public StronaGlownaKlientaView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        H5 h5 = new H5();
        H5 h52 = new H5();
        H5 h53 = new H5();
        H5 h54 = new H5();
        H5 h55 = new H5();
        H5 h56 = new H5();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        Hr hr = new Hr();
        Button buttonPrimary2 = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn3.setWidth("100%");
        layoutColumn3.setMaxWidth("800px");
        layoutColumn3.setHeight("min-content");
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
        h55.setText("Heading");
        h55.setWidth("max-content");
        h56.setText("Należność");
        h56.setWidth("max-content");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Zrezygnuj z zajęć");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Więcej informacji");
        buttonSecondary.setWidth("min-content");
        buttonPrimary2.setText("Zapisz się na nowe zajęcia");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(h3);
        layoutColumn3.add(formLayout2Col);
        formLayout2Col.add(h5);
        formLayout2Col.add(h52);
        formLayout2Col.add(h53);
        formLayout2Col.add(h54);
        formLayout2Col.add(h55);
        formLayout2Col.add(h56);
        layoutColumn3.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
        layoutColumn2.add(hr);
        layoutColumn2.add(buttonPrimary2);

        buttonPrimary2.addClickListener(event -> {
            buttonPrimary2.getUI().ifPresent(ui -> ui.navigate("lista-zajec"));
        });
    }
}
