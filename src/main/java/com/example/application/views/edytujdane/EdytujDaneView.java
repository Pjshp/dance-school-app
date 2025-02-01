package com.example.application.views.edytujdane;

import com.example.application.data.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.services.UserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.LocalDate;

@PageTitle("Edytuj Dane")
@Route("edytuj-dane")
@Menu(order = 1, icon = LineAwesomeIconUrl.EDIT)
@AnonymousAllowed
public class EdytujDaneView extends Composite<VerticalLayout> {

    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;

    @Autowired
    public EdytujDaneView(UserService userService, AuthenticatedUser authenticatedUser) {
        this.userService = userService;
        this.authenticatedUser = authenticatedUser;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3("Zmień dane");

        Span currentDataLabel = new Span();
        authenticatedUser.get().ifPresent(user -> {
            currentDataLabel.getElement().setProperty("innerHTML", "Obecne dane:<br>" +
                    "Imię opiekuna: " + user.getGuardianFirstName() + "<br>" +
                    "Nazwisko opiekuna: " + user.getGuardianLastName() + "<br>" +
                    "Adres e-mail: " + user.getEmail() + "<br>" +
                    "Numer telefonu: " + user.getPhone() + "<br>" +
                    "Imię dziecka: " + user.getChildFirstName() + "<br>" +
                    "Nazwisko dziecka: " + user.getChildLastName());
        });

        FormLayout formLayout2Col = new FormLayout();
        ComboBox<String> comboBox = new ComboBox<>("Wybierz pole do edycji");
        comboBox.setItems("Imię opiekuna", "Nazwisko opiekuna", "Adres e-mail", "Numer telefonu",
                "Imię dziecka", "Nazwisko dziecka");
        TextField textField = new TextField("Wprowadź nową wartość");
        Button button = new Button("Zapisz zmiany");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(currentDataLabel);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(comboBox);
        formLayout2Col.add(textField);
        formLayout2Col.add(button);

        button.addClickListener(event -> {
            String selectedField = comboBox.getValue();
            String newValue = textField.getValue();

            if (selectedField == null || selectedField.isEmpty() || newValue == null || newValue.isEmpty()) {
                Notification.show("Wybierz pole do edycji i wprowadź nową wartość.");
                return;
            }

            authenticatedUser.get().ifPresent(user -> {
                switch (selectedField) {
                    case "Imię opiekuna":
                        user.setGuardianFirstName(newValue);
                        break;
                    case "Nazwisko opiekuna":
                        user.setGuardianLastName(newValue);
                        break;
                    case "Adres e-mail":
                        user.setEmail(newValue);
                        break;
                    case "Numer telefonu":
                        user.setPhone(newValue);
                        break;
                    case "Imię dziecka":
                        user.setChildFirstName(newValue);
                        break;
                    case "Nazwisko dziecka":
                        user.setChildLastName(newValue);
                        break;
                    default:
                        Notification.show("Nieznane pole do edycji.");
                        return;
                }
                userService.update(user);
                Notification.show("Dane zostały zaktualizowane.");
                currentDataLabel.getElement().setProperty("innerHTML", "Obecne dane:<br>" +
                        "Imię opiekuna: " + user.getGuardianFirstName() + "<br>" +
                        "Nazwisko opiekuna: " + user.getGuardianLastName() + "<br>" +
                        "Adres e-mail: " + user.getEmail() + "<br>" +
                        "Numer telefonu: " + user.getPhone() + "<br>" +
                        "Imię dziecka: " + user.getChildFirstName() + "<br>" +
                        "Nazwisko dziecka: " + user.getChildLastName());
            });
        });
    }
}