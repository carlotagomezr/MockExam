package org.vaadin.example;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.models.Student;
import org.vaadin.example.models.ListStudents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */

    private FormLayout form = new FormLayout();
    Grid<Student> grid = new Grid<>(Student.class);

    public MainView(@Autowired GreetService service) throws IOException, InterruptedException {
        add(new H1("Web Application for Student Management - Mock Exam"));

        // form
        configureForm();

        // grid
        configureGrid();

        // export button
        
        
    }

    private void configureGrid() throws IOException, InterruptedException {
        grid.setColumns("id", "firstName", "lastName", "dateOfBirth", "gender");
        ListStudents students = GreetService.getAllStudents();
        ArrayList<Student> studentsList = students.getStudents();
        grid.setItems(studentsList);
        add(grid);

        
    }

    private void configureForm() {
       
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        DatePicker birthDate = new DatePicker("Birth date");
        
        ComboBox<String> genderSelection = new ComboBox<>();
        genderSelection.setItems("Female", "Male");

        form.add(firstName, lastName, birthDate, genderSelection);

    
        // submit button
        Button submit = new Button("Submit", event -> {
            String date = birthDate.getValue().toString(); // Get the date 
            String selectedGender = genderSelection.getValue(); // Get the selected gender
            try {
                GreetService.addStudent(firstName.getValue(),lastName.getValue(), date, selectedGender);
                Notification.show("Student added", 3000, Notification.Position.MIDDLE);
                updateGrid();
            } catch (Exception e) {
                Notification.show("Error adding student", 3000, Notification.Position.MIDDLE);
            }
        });

        form.add(submit);
        add(form);

    }

    private void updateGrid() throws IOException, InterruptedException {
        ListStudents students = GreetService.getAllStudents();
        ArrayList<Student> studentsList = students.getStudents();
        grid.setItems(studentsList);
    }

}
