package com.example.demo;

import java.util.ArrayList;

public class AllStudents {
    private ArrayList<Student> allstudents;

    public ArrayList<Student> getStudents() {
        return allstudents;
    }

    public void setStudents(ArrayList<Student> students) {
        this.allstudents = students;
    }

    public AllStudents(ArrayList<Student> allstudents){
        this.allstudents = new ArrayList<Student>();
    }

    public AllStudents(){

    }
}
