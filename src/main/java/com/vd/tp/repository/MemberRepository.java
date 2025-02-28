package com.vd.tp.repository;

import com.vd.tp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findMemberByMemberCode(String memberCode);
}
