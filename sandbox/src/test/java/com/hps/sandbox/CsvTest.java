package com.hps.sandbox;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class CsvTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Read CSV file content from application properties
        Path csvFilePath = Paths.get("users.csv");
        String csvContent = Files.readString(csvFilePath);

        // Construct the POST request with multipart/form-data
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/console/users/upload"))
                .header("Content-Type", "multipart/form-data")
                .POST(HttpRequest.BodyPublishers.ofString(csvContent))
                .build();

        // Send the request to the server
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print response
        System.out.println("Response code: " + response.statusCode());
        System.out.println("Response body: " + response.body());
    }
}
