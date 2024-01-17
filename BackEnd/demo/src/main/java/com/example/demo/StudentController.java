package com.example.demo;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.FileManagers.CsvManager;
import com.example.demo.FileManagers.JsonManager;
import com.example.demo.models.ListStudents;
import com.example.demo.models.Student;

import org.springframework.web.bind.annotation.GetMapping;


@RestController // don't forget this annotation!!
public class StudentController {

    @PostMapping("/add-student")
    public void addStudent(@RequestBody Student student) throws IOException{
        JsonManager jsonManager = new JsonManager();
        jsonManager.writeOneStudentJson(student);

        // CsvManager csvManager = new CsvManager();
        // csvManager.writeOneStudent(student);

        System.out.println("Student added in csv and json files:");
        System.out.println(student);
    }

    @GetMapping("/get-all-students")
    public ListStudents getMethodName() throws IOException {
        JsonManager jsonManager = new JsonManager();
        ListStudents students = jsonManager.readJson();
        // CsvManager csvManager = new CsvManager();
        // ListStudents csvStudents = csvManager.readCsv();
        return students;
    }
    

    
}
