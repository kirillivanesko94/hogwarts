package ru.hogwarts.school1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPort(){
        return ResponseEntity.ok(port);
    }
}
