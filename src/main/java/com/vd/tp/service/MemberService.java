package com.vd.tp.service;

import com.vd.tp.exception.service.MissingFieldsException;
import com.vd.tp.exception.service.NotFoundException;
import com.vd.tp.model.Member;
import com.vd.tp.model.Reservation;
import com.vd.tp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final ReservationService service;

    public Member addMember(Member member) {
        if (member.getMemberCode() == null) throw new MissingFieldsException("Member code is required");

        if (member.getEmail() == null) throw new MissingFieldsException("Email is required");

        return saveMember(member);
    }

    public Member updateMember(String id, Member member) {
        if (!repository.existsById(id)) throw new NotFoundException("Book with id " + id + " not found");

        return addMember(member);

    }

    public Member saveMember(Member member) {
        return repository.save(member);
    }

    public void deleteById(String id) {
        if (!repository.existsById(id)) throw new NotFoundException("Book with id " + id + " not found");

        repository.deleteById(id);
    }

    public Member addReservation(Member member, Reservation reservation) {
        member.getReservations().add(service.addReservation(reservation));

        return saveMember(member);
    }
}
