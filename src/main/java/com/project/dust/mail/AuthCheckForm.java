package com.project.dust.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AuthCheckForm {
    private String inputCode;
    private String authCode;
    private boolean isValid;

}
