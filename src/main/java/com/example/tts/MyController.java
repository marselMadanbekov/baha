package com.example.tts;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class MyController {

    @GetMapping
    public String mainPage(){
        return "admin/index";
    }


    @PostMapping("/get-audio")
    public ResponseEntity<Map<String, String>> getAudio(@RequestParam String text) {

        Map<String, String> response = new HashMap<>();

        try {
            String filename = TTSService.saveAudio(text);
            response.put("filename", filename);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

}
