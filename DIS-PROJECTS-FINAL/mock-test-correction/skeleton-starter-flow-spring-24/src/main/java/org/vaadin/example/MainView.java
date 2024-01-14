package org.vaadin.example;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
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
    public MainView(@Autowired GreetService service) {

        
        add(new H1("Web Application for Student Management"));
        // FORM TO CREATE A NEW STUDENT 
        // event, so that when we click an option, we read what it is said
        // FORM TO CREATE A NEW STUDENT 
        FormLayout form = new FormLayout();
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        DatePicker birthDate = new DatePicker("Birth date");
        
        ComboBox<String> genderSelection = new ComboBox<>();
        genderSelection.setItems("Female", "Male");

        form.add(firstName, lastName, birthDate, genderSelection);

        // submit button
        Button submit = new Button("Submit", event -> {
            String selectedGender = genderSelection.getValue(); // Get the selected gender
            try {
                GreetService.addStudent(firstName.getValue(),lastName.getValue(), birthDate.getValue(), selectedGender);
                Notification.show("Student added");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        form.add(submit);
        add(form);
        
        // GRID TO DISPLAY ALL STUDENTS

        // BUTTON TO EXPORT ALL STUDENTS TO CSV
       

    }

}
