package com.vd.tp.service;

import com.vd.tp.model.Member;
import com.vd.tp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public Member addMember(Member member) {
        return saveMember(member);
    }

    public Member saveMember(Member member) {
        return repository.save(member);
    }
}
