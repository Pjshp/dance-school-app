package com.example.application.views.stronaglownaklienta;

import com.example.application.data.Course;
import com.example.application.data.Enrollment;
import com.example.application.data.EnrollmentRepository;
import com.example.application.data.User;
import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@PageTitle("Strona Główna Klienta")
@Route(value = "strona-glowna-klienta")
@Menu(order = 4, icon = LineAwesomeIconUrl.HOME_SOLID)
@PermitAll
public class StronaGlownaKlientaView extends Composite<VerticalLayout> {

    private final EnrollmentRepository enrollmentRepository;
    private final AuthenticatedUser authenticatedUser;

    @Autowired
    public StronaGlownaKlientaView(EnrollmentRepository enrollmentRepository, AuthenticatedUser authenticatedUser) {
        this.enrollmentRepository = enrollmentRepository;
        this.authenticatedUser = authenticatedUser;

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        Optional<User> authenticatedUserOptional = authenticatedUser.get();
        if (authenticatedUserOptional.isPresent()) {
            User user = authenticatedUserOptional.get();
            List<Enrollment> enrollments = getEnrollmentsForUser(user);

            for (Enrollment enrollment : enrollments) {
                Course course = enrollment.getCourse();

                VerticalLayout layoutColumn2 = new VerticalLayout();
                layoutColumn2.setWidth("100%");
                layoutColumn2.setMaxWidth("800px");
                layoutColumn2.setHeight("min-content");

                H3 h3 = new H3(course.getCourseName());
                h3.setWidth("100%");

                FormLayout formLayout2Col = new FormLayout();
                formLayout2Col.setWidth("100%");

                H5 h5 = new H5("Dzień tygodnia: " + course.getDay());
                h5.setWidth("max-content");

                H5 h52 = new H5("Cena za miesiąc: " + course.getPrice() + " zł");
                h52.setWidth("max-content");

                H5 h53 = new H5("Godzina: " + course.getTime());
                h53.setWidth("max-content");

                H5 h54 = new H5("Osoba prowadząca: " + course.getTeacher().getFirstName()
                        + " " + course.getTeacher().getLastName());
                h54.setWidth("max-content");

                HorizontalLayout layoutRow = new HorizontalLayout();
                layoutRow.addClassName(Gap.MEDIUM);
                layoutRow.setWidth("100%");
                layoutRow.getStyle().set("flex-grow", "1");

                Button buttonPrimary = new Button("Zrezygnuj z zajęć");
                buttonPrimary.setWidth("min-content");
                buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                buttonPrimary.addClickListener(event -> {
                    enrollmentRepository.delete(enrollment);
                    Notification.show("Zrezygnowano z zajęć: " + course.getCourseName());
                    getContent().remove(layoutColumn2);
                });

                getContent().add(layoutColumn2);
                layoutColumn2.add(h3);
                layoutColumn2.add(formLayout2Col);
                formLayout2Col.add(h5);
                formLayout2Col.add(h52);
                formLayout2Col.add(h53);
                formLayout2Col.add(h54);
                layoutColumn2.add(layoutRow);
                layoutRow.add(buttonPrimary);
            }
        } else {
            Notification.show("Nie udało się załadować zajęć. Użytkownik nie jest zalogowany.");
        }

        Hr hr = new Hr();
        Button buttonPrimary2 = new Button("Zapisz się na nowe zajęcia");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(event -> {
            buttonPrimary2.getUI().ifPresent(ui -> ui.navigate("lista-zajec"));
        });

        getContent().add(hr);
        getContent().add(buttonPrimary2);
    }

    @Transactional
    public List<Enrollment> getEnrollmentsForUser(User user) {
        return enrollmentRepository.findByUserWithCoursesAndTeachers(user.getUserId());
    }
}