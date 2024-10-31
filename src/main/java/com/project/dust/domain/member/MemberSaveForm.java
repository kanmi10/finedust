package com.project.dust.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberSaveForm {

    @NotEmpty
    @Email
    private String loginId;     //로그인 ID

    @NotEmpty
    @Size(min = 2, max = 6)
    private String name;        //사용자 이름

    @NotEmpty
    @Size(min = 8, max = 12)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$") //영문과 숫자 반드시 하나 이상을 포함
    private String password;    //비밀번호

}
