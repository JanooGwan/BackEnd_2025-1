package com.example.bcsd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterResponseDto {

    private Long id;
    private String name;
    private String email;


}
