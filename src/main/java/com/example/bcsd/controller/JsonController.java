package com.example.bcsd.controller;

import com.example.bcsd.controller.dto.AgeAndNameDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

    @GetMapping("/json")
    public AgeAndNameDto ageAndName() {
        return new AgeAndNameDto(24, "이관우");
    }
}
