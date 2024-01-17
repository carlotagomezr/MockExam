package com.example.demo.models;

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

    // method to add a new student
    public void addStudent(Student student){
        this.students.add(student);
    }

    
}
