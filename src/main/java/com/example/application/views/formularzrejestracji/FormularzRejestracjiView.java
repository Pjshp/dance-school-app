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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
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
    private final Binder<User> binder = new Binder<>(User.class);

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

        // Bind fields to the User object and add validation
        binder.forField(firstNameField)
                .asRequired("Imię jest wymagane")
                .bind(User::getGuardianFirstName, User::setGuardianFirstName);

        binder.forField(lastNameField)
                .asRequired("Nazwisko jest wymagane")
                .bind(User::getGuardianLastName, User::setGuardianLastName);

        binder.forField(emailField)
                .asRequired("Adres e-mail jest wymagany")
                .withValidator(new RegexpValidator("Nieprawidłowy adres e-mail", "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(phoneNumberField)
                .asRequired("Numer telefonu jest wymagany")
                .withValidator(new RegexpValidator("Nieprawidłowy format numeru telefonu", "\\d{9}"))
                .bind(User::getPhone, User::setPhone);

        binder.forField(passwordField)
                .asRequired("Hasło jest wymagane")
                .bind(User::getPassword, User::setPassword);

        binder.forField(childFirstNameField)
                .asRequired("Imię dziecka jest wymagane")
                .bind(User::getChildFirstName, User::setChildFirstName);

        binder.forField(childLastNameField)
                .asRequired("Nazwisko dziecka jest wymagane")
                .bind(User::getChildLastName, User::setChildLastName);

        binder.forField(childBirthDateField)
                .asRequired("Data urodzenia jest wymagana")
                .withValidator(date -> {
                    try {
                        LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        return true;
                    } catch (DateTimeParseException e) {
                        return false;
                    }
                }, "Nieprawidłowy format daty. Użyj formatu dd.MM.yyyy.")
                .bind(user -> user.getBirthDate() != null ? user.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "",
                        (user, date) -> user.setBirthDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(event -> {
            User user = new User();
            try {
                binder.writeBean(user);
                user.setRole(Role.USER); // ustawienie domyślnej roli użytkownika
                userService.register(user);
                Notification.show("Rejestracja zakończona sukcesem!");
            } catch (ValidationException e) {
                Notification.show("Nie udało się zarejestrować. Sprawdź poprawność danych.");
            }
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