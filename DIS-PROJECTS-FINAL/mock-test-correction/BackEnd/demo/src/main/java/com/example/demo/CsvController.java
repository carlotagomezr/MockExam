package com.example.demo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CsvController {
    public static ArrayList<Student> readCSV(){
        // Read Students from File
        String filepath = "BackEnd/demo/src/main/resources/students.csv";
        File file = new File(filepath);

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
                    student.setDateOfBirth(LocalDate.parse(nextRecord[3]));
                    student.setGender(nextRecord[4]);

                    System.out.println("Student:");
                    System.out.println(student);

                    // add student to list
                    allStudents.add(student);
                }
                csvReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allStudents;
    }



    public static void writeCSV(Student student){
        String filepath = "BackEnd/demo/src/main/resources/students.csv";
        File file = new File(filepath);
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
          
            LocalDate dateOfBirth = student.getDateOfBirth();
            String dateOfBirthStr = dateOfBirth != null ? dateOfBirth.toString() : "N/A";

            // add data
            String[] data = {student.getId(),student.getFirstName(),student.getLastName(), dateOfBirthStr, student.getGender()};
            writer.writeNext(data);
            writer.close();
            System.out.println("Student added to file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
