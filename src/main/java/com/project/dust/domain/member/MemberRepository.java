package com.project.dust.domain.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member) throws SQLException;

    Member findById(Long id) throws SQLException;

    Optional<Member> findByLoginId(String loginId) throws SQLException;

    List<Member> findAll() throws SQLException;

}
