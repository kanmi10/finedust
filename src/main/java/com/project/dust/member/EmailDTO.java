package com.project.dust.member;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailDTO {

    @NotBlank
    @Email
    private String email;
}
