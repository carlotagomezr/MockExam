package org.vaadin.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable {

    public static void addStudent(String firstName, String lastName,
     LocalDate localDate,String gender) throws IOException, InterruptedException { 
        
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        Student student = new Student(firstName, lastName, localDate, gender);
        // put in json the student
        String body = gson.toJson(student);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add-students"))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
