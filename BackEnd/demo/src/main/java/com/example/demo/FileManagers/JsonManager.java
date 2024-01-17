package com.example.demo.FileManagers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.example.demo.models.ListStudents;
import com.example.demo.models.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonManager {
    private static String filepath = "src/main/java/com/example/demo/Exports/students.json";

    public ListStudents readJson() throws IOException{
        Path path = Paths.get(filepath);

        try {
            if (!Files.exists(Paths.get(filepath))) {
                Files.createFile(Paths.get(filepath));
                return new ListStudents(new ArrayList<Student>());
            }
            else if (Files.size(path) == 0) { // existis but it is empty
                return new ListStudents(new ArrayList<Student>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read the content of the JSON file
        String jsonContent = new String(Files.readAllBytes(path));

        Gson gson = new Gson();
        try {
            ListStudents listStudents = new ListStudents(gson.fromJson(jsonContent,
                                            new TypeToken<ArrayList<Student>>() {}.getType()));
            return listStudents;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void writeOneStudentJson(Student student) throws IOException{
         // Lee el contenido actual del archivo JSON en una lista de objetos Records
        ListStudents liststudents = readJson();

        // Agrega el nuevo objeto Record a la lista existente
        liststudents.addStudent(student);
        // Convierte la lista actualizada a formato JSON
        Gson gson = new Gson();
        String updatedJson = gson.toJson(liststudents.getStudents());

        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(updatedJson);
        }
    }
}
