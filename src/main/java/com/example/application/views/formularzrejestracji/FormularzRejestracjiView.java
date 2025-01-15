package com.example.application.views.formularzrejestracji;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Formularz Rejestracji")
@Route("formularz-rejestracji")
@Menu(order = 1, icon = LineAwesomeIconUrl.USER_PLUS_SOLID)
@AnonymousAllowed
public class FormularzRejestracjiView extends Composite<VerticalLayout> {

    public FormularzRejestracjiView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        H3 h32 = new H3();
        FormLayout formLayout2Col2 = new FormLayout();
        TextField textField5 = new TextField();
        TextField textField6 = new TextField();
        TextField textField7 = new TextField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Informacje o opiekunie");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Imię");
        textField.setWidth("min-content");
        textField2.setLabel("Nazwisko");
        textField2.setWidth("min-content");
        textField3.setLabel("Adres e-mail");
        textField3.setWidth("min-content");
        textField4.setLabel("Numer telefonu");
        textField4.setWidth("min-content");
        h32.setText("Informacje o dziecku");
        h32.setWidth("max-content");
        formLayout2Col2.setWidth("100%");
        textField5.setLabel("Imię");
        textField5.setWidth("min-content");
        textField6.setLabel("Nazwisko");
        textField6.setWidth("min-content");
        textField7.setLabel("Data urodzenia");
        textField7.setWidth("min-content");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Zapisz");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(textField3);
        formLayout2Col.add(textField4);
        layoutColumn2.add(h32);
        layoutColumn2.add(formLayout2Col2);
        formLayout2Col2.add(textField5);
        formLayout2Col2.add(textField6);
        formLayout2Col2.add(textField7);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
    }
}
