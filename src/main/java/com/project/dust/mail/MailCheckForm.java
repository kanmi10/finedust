package com.project.dust.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MailCheckForm {

    private String email;
    private boolean isDuplication;
    private boolean isValid;

}
