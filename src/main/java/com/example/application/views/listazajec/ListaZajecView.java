package com.example.application.views.listazajec;

import com.example.application.data.AgeCategory;
import com.example.application.data.Course;
import com.example.application.data.CourseRepository;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PageTitle("Lista Zajęć")
@Route("lista-zajec")
@Menu(order = 0, icon = LineAwesomeIconUrl.LIST_SOLID)
@PermitAll
public class ListaZajecView extends Composite<VerticalLayout> {

    private final CourseRepository courseRepository;
    private final EnrollmentService enrollmentService;
    private final AuthenticatedUser authenticatedUser;

    private final VerticalLayout courseListLayout = new VerticalLayout();
    private boolean showingFiltered = false;

    @Autowired
    public ListaZajecView(CourseRepository courseRepository, EnrollmentService enrollmentService, AuthenticatedUser authenticatedUser) {
        this.courseRepository = courseRepository;
        this.enrollmentService = enrollmentService;
        this.authenticatedUser = authenticatedUser;

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        Button filterButton = new Button("Pokaż grupy z odpowiednią kategorią wiekową");
        filterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filterButton.addClickListener(event -> {
            if (showingFiltered) {
                displayCourses(courseRepository.findAll());
                filterButton.setText("Pokaż grupy z odpowiednią kategorią wiekową");
            } else {
                filterCoursesByAgeCategory();
                filterButton.setText("Pokaż wszystkie grupy");
            }
            showingFiltered = !showingFiltered;
        });

        VerticalLayout buttonLayout = new VerticalLayout(filterButton);
        getContent().add(buttonLayout);
        getContent().add(courseListLayout);

        displayCourses(courseRepository.findAll());
    }

    private void displayCourses(List<Course> courses) {
        courseListLayout.removeAll();
        for (Course course : courses) {
            VerticalLayout layoutColumn2 = new VerticalLayout();
            layoutColumn2.setWidth("100%");
            layoutColumn2.setMaxWidth("800px");
            layoutColumn2.setHeight("min-content");

            H3 h3 = new H3(course.getCourseName());
            h3.setWidth("100%");

            FormLayout formLayout2Col = new FormLayout();
            formLayout2Col.setWidth("100%");

            H5 dayH5 = new H5("Dzień tygodnia: " + course.getDay());
            dayH5.setWidth("max-content");

            H5 priceH5 = new H5("Cena za miesiąc: " + course.getPrice() + " zł");
            priceH5.setWidth("max-content");

            H5 timeH5 = new H5("Godzina: " + course.getTime());
            timeH5.setWidth("max-content");

            H5 teacherH5 = new H5("Osoba prowadząca: " + course.getTeacher().getFirstName()
                    + " " + course.getTeacher().getLastName());
            teacherH5.setWidth("max-content");

            H5 ageH5 = new H5("Kategoria wiekowa: " + course.getAgeCategory().getDisplayName() + " lat");
            ageH5.setWidth("max-content");

            H5 limitH5 = new H5("Pozostało miejsc: " + (course.getLimitOfPlaces() - enrollmentService.getUsersEnrolledInCourse(course).size()));
            limitH5.setWidth("max-content");

            Paragraph textSmall = new Paragraph(course.getCourseDescription());
            textSmall.setWidth("100%");
            textSmall.getStyle().set("font-size", "var(--lumo-font-size-xs)");
            textSmall.setVisible(false);

            Paragraph textSmall2 = new Paragraph(course.getTeacher().getTeacherDescription());
            textSmall2.setWidth("100%");
            textSmall2.getStyle().set("font-size", "var(--lumo-font-size-xs)");
            textSmall2.setVisible(false);

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
                    int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
                    if (!isAgeInCategory(age, course.getAgeCategory())) {
                        Notification.show("Nie możesz zapisać się na te zajęcia, ponieważ nie pasują do Twojej kategorii wiekowej.");
                        return;
                    }
                    int placesLeft = course.getLimitOfPlaces() - enrollmentService.getUsersEnrolledInCourse(course).size();
                    if (placesLeft == 0) {
                        Notification.show("Brak miejsc w tej grupie.");
                        return;
                    }
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

            courseListLayout.add(layoutColumn2);
            layoutColumn2.add(h3);
            layoutColumn2.add(formLayout2Col);
            formLayout2Col.add(dayH5);
            formLayout2Col.add(priceH5);
            formLayout2Col.add(timeH5);
            formLayout2Col.add(ageH5);
            formLayout2Col.add(limitH5);
            formLayout2Col.add(teacherH5);
            formLayout2Col.add(textSmall);
            formLayout2Col.add(textSmall2);
            layoutColumn2.add(layoutRow);
            layoutRow.add(buttonPrimary);
            layoutRow.add(buttonSecondary);
        }
    }

    private void filterCoursesByAgeCategory() {
        Optional<User> authenticatedUserOptional = authenticatedUser.get();
        if (authenticatedUserOptional.isPresent()) {
            User user = authenticatedUserOptional.get();
            int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
            List<Course> filteredCourses = courseRepository.findAll().stream()
                    .filter(course -> isAgeInCategory(age, course.getAgeCategory()))
                    .collect(Collectors.toList());
            displayCourses(filteredCourses);
        } else {
            Notification.show("Nie udało się załadować zajęć. Użytkownik nie jest zalogowany.");
        }
    }

    private boolean isAgeInCategory(int age, AgeCategory ageCategory) {
        switch (ageCategory) {
            case FROM3TO5:
                return age >= 3 && age <= 5;
            case FROM6TO8:
                return age >= 6 && age <= 8;
            case FROM9TO12:
                return age >= 9 && age <= 12;
            case FROM13TO15:
                return age >= 13 && age <= 15;
            case FROM16TO18:
                return age >= 16 && age <= 18;
            default:
                return false;
        }
    }
}