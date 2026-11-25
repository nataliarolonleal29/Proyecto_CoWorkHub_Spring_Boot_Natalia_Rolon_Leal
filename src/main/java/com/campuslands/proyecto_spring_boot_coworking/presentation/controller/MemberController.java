package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.AdditionalServiceService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MemberService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.AdditionalService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> findAll(){
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findById(@PathVariable Long id){
        Member member = memberService.findById(id).orElseThrow(() -> new IllegalArgumentException("El miembro no existe"));

        return ResponseEntity.ok(member);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> findByEmail(@PathVariable String email){
        Member member = memberService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("No existe un miembro con ese correo electrónico"));

        return ResponseEntity.ok(member);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<Member> findByDocumentNumber(@PathVariable String documentNumber){
        Member member = memberService.findByDocumentNumber(documentNumber).orElseThrow(() -> new IllegalArgumentException("No existe un miembro con ese documento"));

        return ResponseEntity.ok(member);
    }


    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member){
        Member savedMember = memberService.save(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody Member member){
        Member existing = memberService.findById(id).orElseThrow(() -> new IllegalArgumentException("El miembro no existe"));

        existing.setDocumentNumber(member.getDocumentNumber());
        existing.setFullName(member.getFullName());
        existing.setEmail(member.getEmail());
        existing.setStatus(member.getStatus());
        existing.setMembershipPlan(member.getMembershipPlan());

        Member updatedMember = memberService.save(existing);

        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        memberService.findById(id).orElseThrow(() -> new IllegalArgumentException("El miembro no existe"));

        memberService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
