package com.example.application.views.dodajkurs;

import com.example.application.data.AgeCategory;
import com.example.application.data.Course;
import com.example.application.data.Teacher;
import com.example.application.services.CourseService;
import com.example.application.services.TeacherService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.notification.Notification;

@PageTitle("Dodaj Kurs")
@Route("dodaj-kurs")
@Menu(order = 0, icon = LineAwesomeIconUrl.PLUS_SOLID)
@PermitAll
public class DodajKursView extends Composite<VerticalLayout> {
    private final TeacherService teacherService;
    private final CourseService courseService;

    public DodajKursView(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField nameTextField = new TextField();
        ComboBox<String> dayComboBox = new ComboBox<>();
        TimePicker timePicker = new TimePicker();
        TextField priceTextField = new TextField();
        TextArea descriptionTextArea = new TextArea();
        ComboBox<AgeCategory> ageCategoryComboBox = new ComboBox<>();
        ComboBox<Teacher> teacherComboBox = new ComboBox<>();
        TextField limitOfPlacesTextField = new TextField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Dodaj kurs");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nameTextField.setLabel("Nazwa kursu");
        nameTextField.setRequired(true);
        nameTextField.setErrorMessage("Nazwa kursu nie może być pusta");
        dayComboBox.setLabel("Dzień");
        dayComboBox.setRequired(true);
        dayComboBox.setErrorMessage("Wybierz dzień");
        timePicker.setLabel("Godzina");
        timePicker.setMin(LocalTime.of(14, 0));
        timePicker.setMax(LocalTime.of(20, 0));
        timePicker.setRequired(true);
        timePicker.setErrorMessage("Wybierz godzinę");
        priceTextField.setLabel("Cena za miesiąc [zł]");
        priceTextField.setRequired(true);
        priceTextField.setPattern("\\d+(\\.\\d{1,2})?");
        priceTextField.setErrorMessage("Podaj prawidłową cenę");
        limitOfPlacesTextField.setLabel("Limit miejsc");
        limitOfPlacesTextField.setRequired(true);
        limitOfPlacesTextField.setPattern("\\d+");
        limitOfPlacesTextField.setErrorMessage("Podaj prawidłową liczbę");
        descriptionTextArea.setLabel("Opis");
        descriptionTextArea.setRequired(true);
        descriptionTextArea.setErrorMessage("Opis nie może być pusty");
        ageCategoryComboBox.setLabel("Kategoria wiekowa");
        ageCategoryComboBox.setWidth("min-content");
        ageCategoryComboBox.setItems(AgeCategory.values());
        ageCategoryComboBox.setItemLabelGenerator(AgeCategory::getDisplayName);
        ageCategoryComboBox.setRequired(true);
        ageCategoryComboBox.setErrorMessage("Wybierz kategorię wiekową");
        teacherComboBox.setLabel("Prowadzący");
        teacherComboBox.setWidth("min-content");
        setTeacherComboBoxData(teacherComboBox);
        teacherComboBox.setRequired(true);
        teacherComboBox.setErrorMessage("Wybierz prowadzącego");
        setDayComboBoxData(dayComboBox);
        layoutRow.addClassName(Gap.MEDIUM);
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
        formLayout2Col.add(dayComboBox);
        formLayout2Col.add(timePicker);
        formLayout2Col.add(priceTextField);
        formLayout2Col.add(ageCategoryComboBox);
        formLayout2Col.add(teacherComboBox);
        formLayout2Col.add(limitOfPlacesTextField);
        formLayout2Col.add(descriptionTextArea);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);

        buttonPrimary.addClickListener(event -> {
            String name = nameTextField.getValue();
            String day = dayComboBox.getValue();
            LocalTime localTime = timePicker.getValue();
            String priceString = priceTextField.getValue();
            String description = descriptionTextArea.getValue();
            AgeCategory ageCategory = ageCategoryComboBox.getValue();
            Teacher teacher = teacherComboBox.getValue();
            String limitOfPlacesString = limitOfPlacesTextField.getValue();

            if (name.isEmpty() || day.isEmpty() || localTime == null || priceString.isEmpty() || description.isEmpty() || ageCategory == null || teacher == null || limitOfPlacesString.isEmpty()) {
                Notification.show("Wypełnij wszystkie pola");
                return;
            }

            String time = localTime.toString();
            double price = Double.parseDouble(priceString);
            int limitOfPlaces = Integer.parseInt(limitOfPlacesString);

            List<Course> existingCourses = courseService.findAll();
            boolean courseExists = existingCourses.stream()
                    .anyMatch(course -> course.getDay().equals(day) && course.getTime().equals(time));

            if (courseExists) {
                Notification.show("Zajęcia w tym dniu i godzinie są już zajęte.");
                return;
            }

            Course newCourse = new Course();
            newCourse.setCourseName(name);
            newCourse.setDay(day);
            newCourse.setTime(time);
            newCourse.setPrice(price);
            newCourse.setCourseDescription(description);
            newCourse.setAgeCategory(ageCategory);
            newCourse.setTeacher(teacher);
            newCourse.setLimitOfPlaces(limitOfPlaces);

            courseService.save(newCourse);
            Notification.show("Dodano kurs: " + name);

            nameTextField.clear();
            dayComboBox.clear();
            timePicker.clear();
            priceTextField.clear();
            descriptionTextArea.clear();
            ageCategoryComboBox.clear();
            teacherComboBox.clear();
            limitOfPlacesTextField.clear();
        });

        buttonSecondary.addClickListener(event -> {
            buttonSecondary.getUI().ifPresent(ui -> ui.navigate("strona-glowna-pracownika"));
        });
    }

    private void setTeacherComboBoxData(ComboBox<Teacher> comboBox) {
        List<Teacher> teachers = teacherService.findAll();
        comboBox.setItems(teachers);
        comboBox.setItemLabelGenerator(teacher -> teacher.getFirstName() + " " + teacher.getLastName());
    }

    private void setDayComboBoxData(ComboBox<String> comboBox) {
        List<String> days = new ArrayList<>();
        days.add("Poniedziałek");
        days.add("Wtorek");
        days.add("Środa");
        days.add("Czwartek");
        days.add("Piątek");
        comboBox.setItems(days);
    }
}