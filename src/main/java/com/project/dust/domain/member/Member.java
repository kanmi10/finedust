package com.project.dust.domain.member;

import lombok.Data;

@Data
public class Member {

    private Long id;

    private String loginId;     //로그인 ID

    private String name;        //사용자 이름

    private String password;    //비밀번호

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Member() {
    }
}
