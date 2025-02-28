package com.vd.tp.service;

import com.vd.tp.exception.service.BadArgumentException;
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
    private final MailService mailService;

    public Member findMemberByCode(String code) {
        return repository.findMemberByMemberCode(code).orElseThrow(() -> new NotFoundException("Member with code " + code + " not found"));
    }

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
        if (!repository.existsById(member.getId())) throw new NotFoundException("Member not found");

        if (tooManyReservations(member)) throw new BadArgumentException("Member has too many reservations");

        member.getReservations().add(service.addReservation(reservation));
        return saveMember(member);
    }

    public boolean tooManyReservations(Member member) {
        return member.getReservations().stream().filter(r -> !r.isClosed()).toList().size() > 3;
    }

    public void sendMailToMember(Reservation reservation, Member member) {
        if (!repository.existsById(member.getId())) throw new NotFoundException("Member not found");

        if (!service.existByID(reservation.getId())) throw new NotFoundException("Reservation not found");

        mailService.sendMail(member.getEmail());
    }
}
