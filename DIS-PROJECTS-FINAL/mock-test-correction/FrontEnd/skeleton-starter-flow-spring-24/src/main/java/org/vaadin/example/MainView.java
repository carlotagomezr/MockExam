package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route
public class MainView extends VerticalLayout {

    // @param service

    // form, which is a modal dialog
    FormLayout form = new FormLayout();

    // grid
    Grid<Student> grid = new Grid<>(Student.class);

    
    public MainView(@Autowired GreetService service) {
        // form that adds a new student
        setForm();

    }


    private void setForm() {
       
        // add all the fields like the class student has
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        DatePicker datePicker = new DatePicker("Start date");
        TextField gender = new TextField("Gender");
        form.add(firstName, lastName,datePicker,gender);


    }

}
