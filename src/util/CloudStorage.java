package util;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
public class CloudStorage {
	private static final String SUPABASE_URL = "https://opeecacgjysnxvofuptd.supabase.co";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9wZWVjYWNnanlzbnh2b2Z1cHRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzQ0ODAzNDYsImV4cCI6MjA5MDA1NjM0Nn0.K5nDbNgCatNRpeA1qBvcsj7cxRHDNdJrT9XiETCdG8c";
    private static final String BUCKET_NAME = "postFiles";

    public static String uploadFile(File file) throws Exception {
        String uploadUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + file.getName();
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadUrl))
                .header("apikey", API_KEY)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/octet-stream") 
                .POST(HttpRequest.BodyPublishers.ofFile(file.toPath()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return SUPABASE_URL + "/storage/v1/object/public/" + BUCKET_NAME + "/" + file.getName();
        } else {
            throw new RuntimeException("Upload failed: " + response.body());
        }
    }
}
