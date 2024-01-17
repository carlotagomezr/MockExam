package org.vaadin.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

    // CSV EXPORT METHOD
    public static void exportToCSV() {
        // post method to export csv
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/export-csv"))
            .header("Content-Type", "application/json") // important this line!
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Exported to CSV");
            } else {
                System.out.println("Error exporting to CSV");
               
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
 
       
            
    }


    public static void exportToCSVClient() {
       HttpClient client = HttpClient.newHttpClient();

       HttpRequest request = HttpRequest.newBuilder()
       .uri(URI.create("http://localhost:8080/download-json-front"))
       .header("Content-Type", "application/json") // important this line!
       .GET()
       .build();

       try {
              HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
              System.out.println("FILE RECEIVED FROM BACKEND");
              System.out.println("Response: " + response.body());
              // Save response, which would be a file, in the exports folder

              if (response.statusCode() == 200) {
                Files.writeString(Paths.get("src/main/Exports/students-download.json"), response.body(), 
                    StandardOpenOption.CREATE, 
                    StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Exported to CSV");
              } else {
                System.out.println("Error exporting to CSV");
                  
              }
         } catch (IOException | InterruptedException e) {
              e.printStackTrace();
       }
      
       
    }

    // CSV METHOD - DELETE STUDENT FROM CSV FILE
    public static void deleteStudent(String id) throws IOException, InterruptedException {
        // DELETE METHOD 
    
        HttpClient client = HttpClient.newHttpClient();
        System.out.println("DELETE FUNCTION IN DATA SERVICE: " + id);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/delete-student/" + id))
            .header("Content-Type", "application/json") // important this line!
            .DELETE()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Student deleted successfully");
        } else {
            System.out.println("Error in deleting the Student: " + response.body());
        }
    }

    // CSV METHOD - MODIFY STUDENT FROM CSV FILE
    public static void modifyStudent(String id, String fistName, 
            String lastName, String dateofBirth, String gender) throws IOException, InterruptedException {
        // PUT METHOD
        System.out.println("MODIFICATION FUNCTION IN DATA SERVICE");
        HttpClient client = HttpClient.newHttpClient();

        Gson gson = new Gson();

        
        Student student = new Student(fistName, lastName, dateofBirth, gender);
        System.out.println("Student to modify: ");
        String body = gson.toJson(student);// put in json the student
        System.out.println(body);

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/modify-student/" + id)) // enviamos el id por parametro url para saber cual hay que modificar
        .header("Content-type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(body)) // to modify we need to sent the body
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            System.out.println("Student modified successfully");
        } else {
            System.out.println("Error in modifying the Student: " + response.body());
        }

        
    }

}
     