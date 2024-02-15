package com.example.tts;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class TTSService {

    public static String saveAudio(String text){
        try {
            String apiUrl = "http://tts.ulut.kg/api/tts";
            String apiToken = "ejrut5ALjdJE6fj5iMDl7X5bdBahL9Ses2ARLOkLIzMfa3bdmmwI24kzEUi6qZUw";
            String speakerId = "2";

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiToken)
                    .POST(HttpRequest.BodyPublishers.ofString("{\"text\": \"" + text + "\", \"speaker_id\": \"" + speakerId + "\"}"))
                    .build();

            HttpResponse<byte[]> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

            if (httpResponse.statusCode() == 200) {
                String fileName = generateFileName();
                byte[] audioData = httpResponse.body();
                Path filePath = Paths.get("/hackaton3/audio/" + fileName + ".mp3");
                Files.write(filePath, audioData);

                System.out.println("Аудиофайл успешно сохранен: " + fileName + ".mp3");
                return fileName;
            } else {

                return ("Ошибка при загрузке аудио: " + httpResponse.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String generateFileName() {
        return "audio_" + UUID.randomUUID().toString();
    }
}
