package com.project.dust.domain.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;     //로그인 ID
    @NotEmpty
    private String name;        //사용자 이름
    @NotEmpty
    private String password;    //비밀번호

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Member() {
    }
}
