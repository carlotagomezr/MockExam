package org.vaadin.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.vaadin.example.models.ListStudents;

import org.springframework.stereotype.Service;
import org.vaadin.example.models.Student;

import com.nimbusds.jose.shaded.gson.Gson;


@Service
public class GreetService implements Serializable {

    public static void addStudent(String firstName, String lastName,
     String dateofBirth,String gender) {
        
        HttpClient client = HttpClient.newHttpClient();
        
        Gson gson = new Gson();
        
        Student student = new Student(firstName, lastName, dateofBirth, gender);
        // put in json the student
        String body = gson.toJson(student);
        System.out.println(body);
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add-student"))
            .header("Content-Type", "application/json") // important this line!
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(request);

            if (response.statusCode() == 200) {
                System.out.println("Student added");
            } else {
                System.out.println("Error adding student");
                System.out.println(response.body());
            }
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

       

            
    }


    public static ListStudents getAllStudents() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/get-all-students"))
            .header("Content-Type", "application/json") // important this line!
            .GET()
            .build();
         
        Gson gson = new Gson();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jresponse = response.body();
        
        System.out.println("Response: ");
        System.out.println(jresponse);
        ListStudents students = gson.fromJson(jresponse, ListStudents.class);
        System.out.println("Students to send into the main view: ");
        System.out.println(students);
        return students;

        
    }

}
