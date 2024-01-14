package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController; // ensure we have added spring boot starter web dependency

import com.opencsv.CSVWriter;

@RestController
public class StudentController {
    
    @GetMapping("/add-student") // add students to a csv file (so we can export at any time)
    public void addStudentToFile(Student student){ // receive as parameter the student
        String filepath = "src/main/resources/students.csv";
        File file = new File(filepath);
        try {
            if (!file.exists()) // if the file does not exist, create it
                file.createNewFile();
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = {"id", "firstName", "lastName", "dateofBirth", "gender"};
            writer.writeNext(header);

            // add data
            String[] data = {student.getId(), 
                            student.getFirstName(),
                            student.getLastName(), 
                            student.getDateOfBirth().toString(), 
                            student.getGender()};
            writer.writeNext(data);
            writer.close();
            System.out.println("Student added to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
