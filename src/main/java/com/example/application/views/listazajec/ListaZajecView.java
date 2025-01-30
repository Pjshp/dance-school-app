package com.example.application.views.listazajec;

import com.example.application.data.Course;
import com.example.application.data.CourseRepository;
import com.example.application.data.EnrollmentRepository;
import com.example.application.data.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.services.EnrollmentService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.Optional;

@PageTitle("Lista Zajęć")
@Route("lista-zajec")
@Menu(order = 0, icon = LineAwesomeIconUrl.LIST_SOLID)
@PermitAll
public class ListaZajecView extends Composite<VerticalLayout> {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;
    private final AuthenticatedUser authenticatedUser;

    @Autowired
    public ListaZajecView(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, EnrollmentService enrollmentService, AuthenticatedUser authenticatedUser) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentService = enrollmentService;
        this.authenticatedUser = authenticatedUser;

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
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

            Paragraph textSmall = new Paragraph(course.getCourseDescription());
            textSmall.setWidth("100%");
            textSmall.getStyle().set("font-size", "var(--lumo-font-size-xs)");
            textSmall.setVisible(false); // Initially hidden

            Paragraph textSmall2 = new Paragraph(course.getTeacher().getTeacherDescription());
            textSmall2.setWidth("100%");
            textSmall2.getStyle().set("font-size", "var(--lumo-font-size-xs)");
            textSmall2.setVisible(false); // Initially hidden

            HorizontalLayout layoutRow = new HorizontalLayout();
            layoutRow.addClassName(Gap.MEDIUM);
            layoutRow.setWidth("100%");
            layoutRow.getStyle().set("flex-grow", "1");

            Button buttonPrimary = new Button("Zapisz się");
            buttonPrimary.setWidth("min-content");
            buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            buttonPrimary.addClickListener(event -> {
                Optional<User> authenticatedUserOptional = authenticatedUser.get();
                if (authenticatedUserOptional.isPresent()) {
                    User user = authenticatedUserOptional.get();
                    boolean alreadyEnrolled = enrollmentService.isUserEnrolledInCourse(user, course);
                    if (alreadyEnrolled) {
                        Notification.show("Jesteś już zapisany na te zajęcia.");
                    } else {
                        enrollmentService.enrollUserInCourse(user, course);
                        Notification.show("Zapisano na zajęcia: " + course.getCourseName());
                    }
                } else {
                    Notification.show("Nie udało się zapisać na zajęcia. Użytkownik nie jest zalogowany.");
                }
            });

            Button buttonSecondary = new Button("Więcej informacji");
            buttonSecondary.setWidth("min-content");
            buttonSecondary.addClickListener(event -> {
                boolean isVisible = textSmall.isVisible();
                textSmall.setVisible(!isVisible);
                textSmall2.setVisible(!isVisible);
                buttonSecondary.setText(isVisible ? "Więcej informacji" : "Mniej informacji");
            });

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
}