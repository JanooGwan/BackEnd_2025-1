package com.example.bcsd.dto;

import com.example.bcsd.domain.Member;
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

    public static MemberRegisterResponseDto from(Member member) {
        return new MemberRegisterResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail()
        );
    }
}
