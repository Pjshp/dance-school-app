package com.example.application.views.formularzrejestracji;

import com.example.application.data.User;
import com.example.application.data.Role;
import com.example.application.services.UserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@PageTitle("Formularz Rejestracji")
@Route("formularz-rejestracji")
@Menu(order = 1, icon = LineAwesomeIconUrl.USER_PLUS_SOLID)
@AnonymousAllowed
public class FormularzRejestracjiView extends Composite<VerticalLayout> {

    private final UserService userService;

    @Autowired
    public FormularzRejestracjiView(UserService userService) {
        this.userService = userService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3("Informacje o opiekunie");
        FormLayout formLayout2Col = new FormLayout();
        TextField firstNameField = new TextField("Imię");
        TextField lastNameField = new TextField("Nazwisko");
        TextField emailField = new TextField("Adres e-mail");
        TextField phoneNumberField = new TextField("Numer telefonu");
        PasswordField passwordField = new PasswordField("Hasło");
        H3 h32 = new H3("Informacje o dziecku");
        FormLayout formLayout2Col2 = new FormLayout();
        TextField childFirstNameField = new TextField("Imię");
        TextField childLastNameField = new TextField("Nazwisko");
        TextField childBirthDateField = new TextField("Data urodzenia (dd.MM.yyyy)");
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button("Zapisz");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");

        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(event -> {
            User user = new User();
            user.setGuardianFirstName(firstNameField.getValue());
            user.setGuardianLastName(lastNameField.getValue());
            user.setEmail(emailField.getValue());
            user.setPhone(phoneNumberField.getValue());
            user.setPassword(passwordField.getValue());
            user.setChildFirstName(childFirstNameField.getValue());
            user.setChildLastName(childLastNameField.getValue());



            // Parsowanie daty urodzenia dziecka
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate birthDate = LocalDate.parse(childBirthDateField.getValue(), formatter);
                user.setBirthDate(birthDate);
            } catch (DateTimeParseException e) {
                Notification.show("Nieprawidłowy format daty. Użyj formatu dd.MM.yyyy.");
                return;
            }

            user.setRole(Role.USER); // ustawienie domyślnej roli użytkownika

            userService.register(user);
            Notification.show("Rejestracja zakończona sukcesem!");
        });

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(firstNameField);
        formLayout2Col.add(lastNameField);
        formLayout2Col.add(emailField);
        formLayout2Col.add(phoneNumberField);
        formLayout2Col.add(passwordField);
        layoutColumn2.add(h32);
        layoutColumn2.add(formLayout2Col2);
        formLayout2Col2.add(childFirstNameField);
        formLayout2Col2.add(childLastNameField);
        formLayout2Col2.add(childBirthDateField);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
    }
}