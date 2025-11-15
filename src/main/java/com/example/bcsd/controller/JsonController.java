package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.AgeAndName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

    @GetMapping("/json")
    public AgeAndName ageAndName() {
        return new AgeAndName(24, "이관우");
    }
}
