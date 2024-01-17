package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.FileManagers.CsvManager;
import com.example.demo.FileManagers.JsonManager;
import com.example.demo.models.ListStudents;
import com.example.demo.models.Student;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController // don't forget this annotation!!
public class StudentController {

    // JSON AND CSV IMPLEMENTED HERE
    @PostMapping("/add-student")
    public void addStudent(@RequestBody Student student) throws IOException{
        JsonManager jsonManager = new JsonManager();
        jsonManager.writeOneStudentJson(student);

        // CsvManager csvManager = new CsvManager();
        // csvManager.writeOneStudent(student);

        System.out.println("Student added in csv and json files:");
        System.out.println(student);
    }

    // JSON AND CSV IMPLEMENTED HERE
    @GetMapping("/get-all-students")
    public ListStudents getMethodName() throws IOException, CsvValidationException {
        JsonManager jsonManager = new JsonManager();
        ListStudents students = jsonManager.readJson();
        // CsvManager csvManager = new CsvManager();
        // ListStudents csvStudents = csvManager.readCsv();
        return students;
    }

    // EXPORT THE CSV FILE
    @PostMapping("/export-csv") // copy file in another location in backend
    public void exportFile() {
        String download_path = "src/main/java/com/example/demo/Downloads/download-students.csv";
        String source_path = "src/main/java/com/example/demo/Exports/students.json"; // we just need to change this
        
        try {
        // copy the csv file
        Files.copy(Paths.get(source_path), Paths.get(download_path), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File downloaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error downloading file");
            
        }
    }

    // // DELETE IN THE CSV FILE
    // @DeleteMapping("/delete-student/{id}")
    // public void deleteStudent(@PathVariable("id") String id){
    //     // read file
    //     CsvManager csvManager = new CsvManager();
       
    //     try {
    //         ListStudents csvStudents = csvManager.readCsv();
    //         ArrayList<Student> allStudents = csvStudents.getStudents();
    //         // take the record where id = id from path variable and delete that entry
    //         for (Student student : allStudents) {
    //             if (student.getId().equals(id)) {
    //                 allStudents.remove(student);
    //                 break;
    //             }
    //         }

    //         // Change back allstudents to a ListStudents object
    //         ListStudents newCsvStudents = new ListStudents(allStudents);
    //         csvManager.writeAllStudents(newCsvStudents);
    //         // write again in same line
    //     } catch (CsvValidationException e) {
    //         e.printStackTrace();
    //     }
    // }


    // // MODIFY THE CSV FILE
    // @PutMapping("/modify-student/{id}")
    // public void modifyStudents(@PathVariable String id, @RequestBody Student student) throws CsvValidationException {
    //     CsvManager csvManager = new CsvManager();
    //     ListStudents allStudents = csvManager.readCsv();
    //     ArrayList<Student> arrayStudents = allStudents.getStudents();

    //     System.out.println("Student to modify:");
        
    //     String firstName = student.getFirstName();
    //     String lastName = student.getLastName();
    //     String date = student.getDateOfBirth();
    //     String gender = student.getGender();
    //     System.out.println(firstName + "-" + lastName + "-" +date +"-" + gender);
        
    //     for (Student st : arrayStudents) {
    //         if (st.getId().equals(id)) {
    //             // re-write the student with new values
    //             st.setFirstName(firstName);
    //             st.setLastName(lastName);
    //             st.setDateOfBirth(date);
    //             st.setGender(gender);
    //             break;
    //         }
    //     }
    //     // Convert Array to ListStudents object
    //     ListStudents newCsvStudents = new ListStudents(arrayStudents);
    //     csvManager.writeAllStudents(newCsvStudents);

    // }


    // DELETE IN THE JSON FILE
    @DeleteMapping("/delete-student/{id}")
    public void deleteStudentFromJSON(@PathVariable("id") String id) throws IOException{
        // read file
        JsonManager jsonManager = new JsonManager();
       
        try {
            ListStudents jsonStudents = jsonManager.readJson();
            ArrayList<Student> allStudents = jsonStudents.getStudents();
            // take the record where id = id from path variable and delete that entry
            for (Student student : allStudents) {
                if (student.getId().equals(id)) {
                    allStudents.remove(student);
                    break;
                }
            }

            // Change back allstudents to a ListStudents object
            ListStudents newStudents = new ListStudents(allStudents);
            jsonManager.writeAllStudentsJSON(newStudents);
            // write again in same line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // MODIFY THE JSON FILE
    @PutMapping("/modify-student/{id}")
    public void modifyStudentsJSON(@PathVariable String id, @RequestBody Student student) throws CsvValidationException, IOException {
        JsonManager jsonManager = new JsonManager();
        ListStudents allStudents = jsonManager.readJson();
        ArrayList<Student> arrayStudents = allStudents.getStudents();

        System.out.println("Student to modify:");
        
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String date = student.getDateOfBirth();
        String gender = student.getGender();
        System.out.println(firstName + "-" + lastName + "-" +date +"-" + gender);
        
        for (Student st : arrayStudents) {
            if (st.getId().equals(id)) {
                // re-write the student with new values
                st.setFirstName(firstName);
                st.setLastName(lastName);
                st.setDateOfBirth(date);
                st.setGender(gender);
                break;
            }
        }
        // Convert Array to ListStudents object
        ListStudents newStudents = new ListStudents(arrayStudents);
        jsonManager.writeAllStudentsJSON(newStudents);

    }
    
}
