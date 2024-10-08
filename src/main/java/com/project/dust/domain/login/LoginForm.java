package com.project.dust.domain.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty
    private String LoginId;

    @NotEmpty
    private String password;

}
