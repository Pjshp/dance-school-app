package com.example.application.views.dodajprowadzacego;

import com.example.application.data.Teacher;
import com.example.application.services.TeacherService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Dodaj Prowadzącego")
@Route("dodaj-prowadzacego")
@Menu(order = 0, icon = LineAwesomeIconUrl.PLUS_SOLID)
@PermitAll
public class DodajProwadzacegoView extends Composite<VerticalLayout> {
    private final TeacherService teacherService;

    public DodajProwadzacegoView(TeacherService teacherService) {
        this.teacherService = teacherService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField nameTextField = new TextField();
        TextField surnameTextField = new TextField();
        //TextField textField3 = new TextField();
        TextArea descriptionTextArea = new TextArea();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Dodaj prowadzącego");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nameTextField.setLabel("Imię");
        nameTextField.setRequiredIndicatorVisible(true);
        nameTextField.setPattern("[a-zA-Z]*");
        nameTextField.setErrorMessage("Pole jest wymagane i musi zawierać tylko litery.");
        surnameTextField.setLabel("Nazwisko");
        descriptionTextArea.setLabel("Opis");
//        textField3.setLabel("Opis");
//        textField3.setWidth("100%");
//        textField3.setHeight("150px");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Zapisz");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Anuluj");
        buttonSecondary.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nameTextField);
        formLayout2Col.add(surnameTextField);
        formLayout2Col.add(descriptionTextArea);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);

        buttonPrimary.addClickListener(event -> {
            String firstName = nameTextField.getValue();
            String lastName = surnameTextField.getValue();
            String description = descriptionTextArea.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || description.isEmpty()) {
                Notification.show("Wszystkie pola są wymagane.");
                return;
            }

            Teacher newTeacher = new Teacher();
            newTeacher.setFirstName(firstName);
            newTeacher.setLastName(lastName);
            newTeacher.setTeacherDescription(description);

            teacherService.save(newTeacher);
            Notification.show("Prowadzący został dodany.");
        });

        buttonSecondary.addClickListener(event -> {
            buttonSecondary.getUI().ifPresent(ui -> ui.navigate("strona-glowna-pracownika"));
        });
    }
}
