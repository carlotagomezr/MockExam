package org.vaadin.example.models;

import java.util.ArrayList;

public class ListStudents {
    private ArrayList<Student> students;

    public ArrayList<Student> getStudents() { // getter 
        return students;
    }
 
    public void setStudents(ArrayList<Student> students) { // setter
        this.students = students;
    }


    public ListStudents(ArrayList<Student> students){ // function that returns the students
        this.students = students;
    }

    
}
