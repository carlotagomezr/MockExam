package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController; // ensure we have added spring boot starter web dependency


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class StudentController {
    
    @PostMapping("/add-student") // add students to a csv file (so we can export at any time)
    public void addStudentToFile(@RequestBody Student student){ // receive as parameter the student
        System.out.println("Student received:");
        System.out.println(student);
        CsvController.writeOneStudentCSV(student);
    }


    @GetMapping("/get-all-students")
    public ArrayList<Student> getAllStudents() { // in get request we usually don't have parameters here, unless we are fetchin a specific piece of information
        // get students from file and return them as a list of students
        ArrayList<Student> allStudents = CsvController.readCSV();
        System.out.println("All students:");
        System.out.println(allStudents);
        return allStudents;
        
    }

    @PostMapping("/download-students") 
    public void exportCSV() {
        // Create the csv file in the (my-todo/src/main/exports) folder
        String download_path = "BackEnd/demo/src/main/exports/downloaded-students.csv";
        String file_path = "BackEnd/demo/src/main/resources/students.csv";
        
        try {
        // copy the csv file
        Files.copy(Paths.get(file_path), Paths.get(download_path), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File downloaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error downloading file");
            
        }
        
    }


    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable("id") String id){
        // read file
        ArrayList<Student> allStudents = CsvController.readCSV();
        // modify
        ArrayList<Student> updatedStudents = allStudents;
        // take the record where id = id from path variable and delete that entry
        for (Student student : allStudents) {
            if (student.getId().equals(id)) {
                updatedStudents.remove(student);
                break;
            }
        }

        // change arraylist to AllStudnets class type
        AllStudents students = new AllStudents(updatedStudents);

        // write again in same line
        CsvController.writeAllStudentsCSV(students);
    
    
    
    
    }

    


    

    
}
