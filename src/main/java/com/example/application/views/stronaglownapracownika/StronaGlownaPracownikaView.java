package com.example.application.views.stronaglownapracownika;

import com.example.application.data.Course;
import com.example.application.data.User;
import com.example.application.services.CourseService;
import com.example.application.services.EnrollmentService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Strona Główna Pracownika")
@Route("strona-glowna-pracownika")
@Menu(order = 2, icon = LineAwesomeIconUrl.HOME_SOLID)
@PermitAll
@Uses(Icon.class)
public class StronaGlownaPracownikaView extends Composite<VerticalLayout> {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    private final H5 instructorField = new H5();
    private final H5 priceField = new H5();
    private final Grid<User> grid = new Grid<>(User.class);

    @Autowired
    public StronaGlownaPracownikaView(UserService userService, CourseService courseService, EnrollmentService enrollmentService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;

        try {
            Tabs tabs = new Tabs();
            VerticalLayout layoutColumn2 = new VerticalLayout();
            H3 courseNameField = new H3();
            FormLayout formLayout2Col = new FormLayout();
            H5 groupSizeField = new H5();
            HorizontalLayout layoutRow = new HorizontalLayout();
            HorizontalLayout layoutRow2 = new HorizontalLayout();
            Button buttonPrimary = new Button();
            Button buttonSecondary = new Button();
            Button buttonTertiary = new Button();
            VerticalLayout layoutColumn3 = new VerticalLayout();
            H3 h32 = new H3("Edycja należności uczestnika");
            TextField textField = new TextField("Wprowadź nową należność");
            Button buttonChange = new Button();

            getContent().setWidth("100%");
            getContent().getStyle().set("flex-grow", "1");
            getContent().setJustifyContentMode(JustifyContentMode.START);
            getContent().setAlignItems(Alignment.CENTER);

            tabs.setWidth("100%");
            setTabsFromCourses(tabs, courseNameField, groupSizeField);

            layoutColumn2.setWidth("100%");
            layoutColumn2.setMaxWidth("800px");
            layoutColumn2.setHeight("min-content");
            courseNameField.setText("Nazwa zajęć");
            courseNameField.setWidth("100%");
            formLayout2Col.setWidth("100%");
            instructorField.setText("Prowadzący");
            instructorField.setWidth("max-content");
            priceField.setText("Kwota za miesiąc");
            priceField.setWidth("max-content");
            groupSizeField.setText("Ilość osób w grupie: x");
            groupSizeField.setWidth("max-content");
            //multiSelectGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.setWidth("100%");
            grid.getStyle().set("flex-grow", "0");
            layoutRow.addClassName(Gap.MEDIUM);
            layoutRow.setWidth("100%");
            layoutRow.getStyle().set("flex-grow", "1");
            layoutRow2.addClassName(Gap.MEDIUM);
            layoutRow2.setWidth("100%");
            layoutRow2.getStyle().set("flex-grow", "1");
            buttonPrimary.setText("Pokaż skład grupy");
            buttonPrimary.setWidth("min-content");
            buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            buttonSecondary.setText("Edytuj należność uczestnika");
            buttonSecondary.setWidth("min-content");
            buttonTertiary.setText("Usuń uczestnika z grupy");
            buttonTertiary.setWidth("min-content");
            buttonTertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            layoutColumn3.setWidthFull();
            getContent().setFlexGrow(1.0, layoutColumn3);
            layoutColumn3.setWidth("100%");
            layoutColumn3.setMaxWidth("800px");
            layoutColumn3.getStyle().set("flex-grow", "1");

            buttonChange.setText("Ustaw wpisaną należność");
            buttonChange.setWidth("min-content");
            buttonChange.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            getContent().add(tabs);
            getContent().add(layoutColumn2);
            layoutColumn2.add(courseNameField);
            layoutColumn2.add(formLayout2Col);
            formLayout2Col.add(instructorField);
            formLayout2Col.add(priceField);
            formLayout2Col.add(groupSizeField);
            layoutColumn2.add(grid);
            layoutColumn2.add(layoutRow);
            layoutRow.add(layoutRow2);
            layoutRow2.add(buttonPrimary);
            layoutRow2.add(buttonSecondary);
            layoutRow2.add(buttonTertiary);
            getContent().add(layoutColumn3);
            layoutColumn3.add(h32);
            layoutColumn3.add(textField);
            layoutColumn3.add(buttonChange);

            h32.setVisible(false);
            textField.setVisible(false);
            buttonChange.setVisible(false);

            List<Course> courses = courseService.findAll();
            if (!courses.isEmpty()) {
                updateCourseDetails(courses.get(0), courseNameField, groupSizeField);
            }

            buttonSecondary.addClickListener(event -> {
                boolean isVisible = textField.isVisible();
                textField.setVisible(!isVisible);
                h32.setVisible(!isVisible);
                buttonSecondary.setText(isVisible ? "Edytuj należność uczestnika" : "Anuluj edytowanie należności");
                buttonChange.setVisible(!isVisible);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing StronaGlownaPracownikaView", e);
        }
    }

    private void setTabsFromCourses(Tabs tabs, H3 courseNameField, H5 groupSizeField) {
        List<Course> courses = courseService.findAll();
        for (Course course : courses) {
            Tab courseTab = new Tab(course.getCourseName());
            tabs.add(courseTab);
            tabs.addSelectedChangeListener(event -> {
                Tab selectedTab = event.getSelectedTab();
                if (selectedTab.equals(courseTab)) {
                    updateCourseDetails(course, courseNameField, groupSizeField);
                }
            });
        }
    }

    private void setGridSampleData(Grid<User> grid) {

    }

    private void updateCourseDetails(Course course, H3 courseNameField, H5 groupSizeField) {
        courseNameField.setText(course.getCourseName());
        instructorField.setText("Prowadzący: " + course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName());
        priceField.setText("Kwota za miesiąc: " + course.getPrice());

        List<User> users = enrollmentService.getUsersEnrolledInCourse(course);

        groupSizeField.setText("Ilość osób w grupie: " + users.size());
        grid.setItems(users);
    }

//    private void setGridSampleData(Grid<User> grid) {
//        grid.setItems(query -> userService.list(
//                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//    }
}