package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ConflictException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MemberService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Optional<Member> findByDocumentNumber(String documentNumber) {
        return memberRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    @Transactional
    public Member save(Member member) {

        memberRepository.findByEmail(member.getEmail()).ifPresent(existing -> {
            if (!Objects.equals(existing.getId(), member.getId())) {
                throw new ConflictException("El correo electrónico ya está registrado");
            }
        });

        memberRepository.findByDocumentNumber(member.getDocumentNumber()).ifPresent(existing -> {
            if (!Objects.equals(existing.getId(), member.getId())) {
                throw new ConflictException("El documento ya está registrado");
            }
        });

        return memberRepository.save(member);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el miembro con ID: " + id);
        }

        if (memberRepository.existsByIdAndBookingsIsNotEmpty(id)) {
            throw new BusinessRuleException("No se puede eliminar el miembro porque tiene reservas asociadas");
        }

        if (memberRepository.existsByIdAndUserAccountIsNotNull(id)) {
            throw new BusinessRuleException("No se puede eliminar el miembro porque tiene una cuenta de usuario asociada");
        }

        memberRepository.deleteById(id);
    }


    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return memberRepository.existsByDocumentNumber(documentNumber);
    }
}
