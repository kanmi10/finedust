package com.project.dust.member;

import com.project.dust.mail.AuthCheckForm;
import com.project.dust.web.validation.validationgroup.EmailGroup;
import com.project.dust.web.validation.validationgroup.NotBlankGroup;
import com.project.dust.web.validation.validationgroup.PatternGroup;
import com.project.dust.web.validation.validationgroup.SizeGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemberSaveForm {

    private String loginId;     //로그인 ID
    private String authcCode;   //인증 코드

    @NotBlank(groups = NotBlankGroup.class)
    @Size(min = 2, max = 6, groups = SizeGroup.class)
    private String name;        //사용자 이름

    @NotBlank(groups = NotBlankGroup.class)
    @Size(min = 8, max = 12, groups = SizeGroup.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", groups = PatternGroup.class) //영문과 숫자 반드시 하나 이상을 포함
    private String password;    //비밀번호

}
