package com.example.demo.FileManagers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.example.demo.models.ListStudents;
import com.example.demo.models.Student;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


public class CsvManager {
    private static String path = "src/main/java/com/example/demo/Exports/students.csv";

    public ListStudents readCsv() throws CsvValidationException {
        File file = new File(path);
        ArrayList<Student> allStudents = new ArrayList<Student>();        

        try {
            if (file.exists()) {
                FileReader filereader = new FileReader(file);
                CSVReader csvReader = new CSVReader(filereader);
                String[] nextRecord;

                // read data line by line
                while ((nextRecord = csvReader.readNext()) != null) {
                    Student student = new Student(null, null, null, null);
                    student.setId(nextRecord[0]);
                    student.setFirstName(nextRecord[1]);
                    student.setLastName(nextRecord[2]);
                    student.setDateOfBirth(nextRecord[3]);
                    student.setGender(nextRecord[4]);
                    // add student to list
                    allStudents.add(student);
                }
                csvReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListStudents listStudents = new ListStudents(allStudents);
        return listStudents;
    }


    public void writeOneStudent(Student student){
        File file = new File(path);
        try {
            if (!file.exists()) // if the file does not exist, create it
                file.createNewFile();
                System.out.println("File created");
            
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);

            System.out.println(student.getId());
            System.out.println(student.getFirstName());
            System.out.println(student.getLastName());
            System.out.println(student.getDateOfBirth());
            System.out.println(student.getGender());
          
            // add data
            String[] data = {student.getId(),student.getFirstName(),student.getLastName(),
                 student.getDateOfBirth(), student.getGender()};
            writer.writeNext(data);
            writer.close();
            System.out.println("Student added to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
