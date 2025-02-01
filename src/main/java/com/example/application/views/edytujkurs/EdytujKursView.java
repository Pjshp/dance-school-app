package com.example.application.views.edytujkurs;

import com.example.application.data.Course;
import com.example.application.services.CourseService;
import com.example.application.services.EnrollmentService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;

@PageTitle("Edytuj Kurs")
@Route("edytuj-kurs")
@Menu(order = 1, icon = LineAwesomeIconUrl.EDIT)
@AnonymousAllowed
public class EdytujKursView extends Composite<VerticalLayout> {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @Autowired
    public EdytujKursView(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3("Zmień dane kursu");

        ComboBox<Course> courseComboBox = new ComboBox<>("Wybierz kurs");
        List<Course> courses = courseService.findAll();
        courseComboBox.setItems(courses);
        courseComboBox.setItemLabelGenerator(Course::getCourseName);

        Span currentDataLabel = new Span();

        courseComboBox.addValueChangeListener(event -> {
            Course selectedCourse = event.getValue();
            if (selectedCourse != null) {
                int currentParticipants = enrollmentService.getUsersEnrolledInCourse(selectedCourse).size();
                currentDataLabel.getElement().setProperty("innerHTML", "Obecne dane kursu:<br>" +
                        "Nazwa kursu: " + selectedCourse.getCourseName() + "<br>" +
                        "Opis kursu: " + selectedCourse.getCourseDescription() + "<br>" +
                        "Limit miejsc: " + selectedCourse.getLimitOfPlaces() + "<br>" +
                        "Obecna liczba uczestników: " + currentParticipants);
            } else {
                currentDataLabel.setText("");
            }
        });

        FormLayout formLayout2Col = new FormLayout();
        ComboBox<String> fieldComboBox = new ComboBox<>("Wybierz pole do edycji");
        fieldComboBox.setItems("Nazwa kursu", "Opis kursu", "Limit miejsc");
        TextArea textField = new TextArea("Wprowadź nową wartość");
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
        layoutColumn2.add(courseComboBox);
        layoutColumn2.add(currentDataLabel);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(fieldComboBox);
        formLayout2Col.add(textField);
        formLayout2Col.add(button);

        button.addClickListener(event -> {
            Course selectedCourse = courseComboBox.getValue();
            String selectedField = fieldComboBox.getValue();
            String newValue = textField.getValue();

            if (selectedCourse == null) {
                Notification.show("Wybierz kurs do edycji.");
                return;
            }

            if (selectedField == null || selectedField.isEmpty() || newValue == null || newValue.isEmpty()) {
                Notification.show("Wybierz pole do edycji i wprowadź nową wartość.");
                return;
            }

            switch (selectedField) {
                case "Nazwa kursu":
                    selectedCourse.setCourseName(newValue);
                    break;
                case "Opis kursu":
                    selectedCourse.setCourseDescription(newValue);
                    break;
                case "Limit miejsc":
                    int newLimit = Integer.parseInt(newValue);
                    int currentParticipants = enrollmentService.getUsersEnrolledInCourse(selectedCourse).size();
                    if (newLimit < currentParticipants) {
                        Notification.show("Limit miejsc nie może być mniejszy niż liczba obecnych uczestników (" + currentParticipants + ").");
                        return;
                    }
                    selectedCourse.setLimitOfPlaces(newLimit);
                    break;
                default:
                    Notification.show("Nieznane pole do edycji.");
                    return;
            }
            courseService.update(selectedCourse);
            Notification.show("Dane kursu zostały zaktualizowane.");
            int currentParticipants = enrollmentService.getUsersEnrolledInCourse(selectedCourse).size();
            currentDataLabel.getElement().setProperty("innerHTML", "Obecne dane kursu:<br>" +
                    "Nazwa kursu: " + selectedCourse.getCourseName() + "<br>" +
                    "Opis kursu: " + selectedCourse.getCourseDescription() + "<br>" +
                    "Limit miejsc: " + selectedCourse.getLimitOfPlaces() + "<br>" +
                    "Obecna liczba uczestników: " + currentParticipants);

            // Refresh the ComboBox items
            List<Course> updatedCourses = courseService.findAll();
            courseComboBox.setItems(updatedCourses);
        });
    }
}