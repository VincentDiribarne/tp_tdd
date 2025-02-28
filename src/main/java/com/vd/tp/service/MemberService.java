package com.vd.tp.service;

import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.model.Member;
import com.vd.tp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public Member addMember(Member member) {
        if (member.getMemberCode() == null) throw new MissingFieldsException("Member code is required");

        if (member.getEmail() == null) throw new MissingFieldsException("Email is required");

        return saveMember(member);
    }

    public Member saveMember(Member member) {
        return repository.save(member);
    }
}
