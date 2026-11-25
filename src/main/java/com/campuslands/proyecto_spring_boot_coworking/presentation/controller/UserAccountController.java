package com.campuslands.proyecto_spring_boot_coworking.presentation.controller;

import com.campuslands.proyecto_spring_boot_coworking.business.service.LocationService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.MemberService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.RoomService;
import com.campuslands.proyecto_spring_boot_coworking.business.service.UserAccountService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Location;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Room;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.UserAccountRequestDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.dto.UserAccountResponseDTO;
import com.campuslands.proyecto_spring_boot_coworking.presentation.mapper.UserAccountMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-accounts")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final MemberService memberService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountController(UserAccountService userAccountService, MemberService memberService, UserAccountMapper userAccountMapper) {
        this.userAccountService = userAccountService;
        this.memberService = memberService;
        this.userAccountMapper = userAccountMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserAccountResponseDTO>> findAll(){

        List<UserAccountResponseDTO> accounts = userAccountService.findAll().stream().map(userAccountMapper::toDTO).toList();

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountResponseDTO> findById(@PathVariable Long id){
        UserAccount account = userAccountService.findById(id).orElseThrow(() -> new IllegalArgumentException("La cuenta no existe"));

        return ResponseEntity.ok(userAccountMapper.toDTO(account));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserAccountResponseDTO> findByUsername(@PathVariable String username){
        UserAccount account = userAccountService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("La cuenta no existe"));

        return ResponseEntity.ok(userAccountMapper.toDTO(account));
    }

    @PostMapping
    public ResponseEntity<UserAccountResponseDTO> create(@Valid @RequestBody UserAccountRequestDTO request){
        UserAccount account = new UserAccount();

        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());
        account.setRole(request.getRole());

        if (request.getMemberId() != null){
            Member member = memberService.findById(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("El miembro asociado no existe"));

            account.setMember(member);
        }

        UserAccount savedAccount = userAccountService.save(account);

        return ResponseEntity.status(HttpStatus.CREATED).body(userAccountMapper.toDTO(savedAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userAccountService.findById(id).orElseThrow(() -> new IllegalArgumentException("La cuenta no existe"));

        userAccountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
