package org.vaadin.example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.models.Student;
import org.vaadin.example.models.ListStudents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
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
    private Grid<Student> grid = new Grid<>(Student.class);
    private Button export = new Button("Export to CSV");


    public MainView(@Autowired GreetService service) throws IOException, InterruptedException {
        add(new H1("Web Application for Student Management - Mock Exam"));

        // form
        configureForm();

        // grid
        configureGrid();

        // export button
        configureExportButton();

        // Modify or delete buttons when clicking the grid
        configureGridButtons();
        
        
    }

    private void configureExportButton() {
        export.addClickListener(event -> {
            try {
                GreetService.exportToCSV();
                GreetService.exportToCSVClient();
                Notification.show("Exported to CSV", 3000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Error exporting to CSV", 3000, Notification.Position.MIDDLE);
            }
        });
        add(export);   
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

    private void configureGridButtons() {
        grid.addItemClickListener(evento -> {
            // modal dialog with two buttons: delete and modify
            Student student = evento.getItem();
            Dialog dialog = new Dialog();
            FormLayout form = new FormLayout();
            Button deleteButton = new Button("Delete", evento_delete -> {
                try {
                    GreetService.deleteStudent(student.getId());
                    Notification.show("Student deleted", 3000, Notification.Position.MIDDLE);
                    updateGrid();
                } catch (Exception e) {
                    Notification.show("Error deleting student", 3000, Notification.Position.MIDDLE);
                }
                dialog.close();
            });

            Button modifyButton = new Button("Modify", evento_modificar -> {
                try {
                    // open a dialog - form to put the new student
                    Dialog dialog2 = new Dialog();
                    FormLayout form2 = new FormLayout();
                    TextField firstName2 = new TextField("First name");
                    TextField lastName2 = new TextField("Last name");
                    DatePicker birthDate2 = new DatePicker("Birth date");
                    
                    ComboBox<String> genderSelection2 = new ComboBox<>();
                    genderSelection2.setItems("Female", "Male");

    
                    
                    // Add values by default
                    firstName2.setValue(student.getFirstName());
                    lastName2.setValue(student.getLastName());

                    // convert string to date and then to localdate
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(student.getDateOfBirth());
                    birthDate2.setValue(date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                    
                    genderSelection2.setValue(student.getGender());
                    
                    form2.add(firstName2, lastName2, birthDate2, genderSelection2);
                    
                    
                    Button confirmButton = new Button("Confirm modification", event_change ->{
                        try{
                            GreetService.modifyStudent(student.getId(), firstName2.getValue(), lastName2.getValue(),
                            birthDate2.getValue().toString(), genderSelection2.getValue()); // put here the get values from the form fields!!
                            Notification.show("Student modified", 3000, Notification.Position.MIDDLE);
                            updateGrid();
                            dialog2.close();
                        } catch (Exception e) {
                            Notification.show("Error modifying student", 3000, Notification.Position.MIDDLE);
                        }
                    });
                    
                    dialog2.setHeaderTitle("Modify student");
                    dialog2.add(form2,confirmButton);
                    dialog2.open();


                } catch (Exception e) {
                    Notification.show("Error modifying student");
                    e.printStackTrace();
                }
                dialog.close();
            });



            form.add(modifyButton, deleteButton);
            dialog.add(form);
            dialog.open(); // show dialog
        });
       
    }

    


}
