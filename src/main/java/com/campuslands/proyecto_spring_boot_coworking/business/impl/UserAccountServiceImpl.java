package com.campuslands.proyecto_spring_boot_coworking.business.impl;

import com.campuslands.proyecto_spring_boot_coworking.business.exception.BusinessRuleException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ConflictException;
import com.campuslands.proyecto_spring_boot_coworking.business.exception.ResourceNotFoundException;
import com.campuslands.proyecto_spring_boot_coworking.business.service.UserAccountService;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Member;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.Role;
import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.MemberRepository;
import com.campuslands.proyecto_spring_boot_coworking.persistence.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    @Override
    public Optional<UserAccount> findById(Long id) {
        return Optional.of(userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró la cuenta con ID: " + id)));
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return Optional.of(userAccountRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("No se encontró la cuenta con usuario: " + username)));
    }


    @Override
    @Transactional
    public UserAccount save(UserAccount userAccount) {

        // Comprobar que el nombre de usuario no esté repetido
        userAccountRepository.findByUsername(userAccount.getUsername()).ifPresent(existing -> {
            if (!Objects.equals(existing.getId(), userAccount.getId())) {
                throw new ConflictException("El nombre de usuario ya está registrado");
            }
        });

        // Validar la relación con Member según el rol
        if (userAccount.getRole() == Role.MEMBER) {

            if (userAccount.getMember() == null || userAccount.getMember().getId() == null) {
                throw new BusinessRuleException("Una cuenta con rol MEMBER debe estar asociada a un miembro");
            }

            Long memberId = userAccount.getMember().getId();

            // Buscar el miembro real en la base de datos
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("No se encontró el miembro con ID: " + memberId));

            // Comprobar si ese miembro ya tiene otra cuenta
            Optional<UserAccount> existingAccount = userAccountRepository.findByMember_Id(memberId);

            if (existingAccount.isPresent() && !Objects.equals(existingAccount.get().getId(), userAccount.getId())) {
                throw new ConflictException("Este miembro ya tiene una cuenta de usuario");
            }

            userAccount.setMember(member);

        } else {

            if (userAccount.getMember() != null) {
                throw new BusinessRuleException("Las cuentas ADMIN y RECEPTIONIST no deben estar asociadas a un miembro");
            }
        }

        if (userAccount.getPassword() == null || userAccount.getPassword().isBlank()) {
            throw new BusinessRuleException("La contraseña es obligatoria");
        }
        // Contraseña
        String encodedPassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encodedPassword);

        // Guardar la cuenta
        return userAccountRepository.save(userAccount);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        if (!userAccountRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró la cuenta con ID: " + id);
        }

        userAccountRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserName(String username) {
        return userAccountRepository.existsByUsername(username);
    }
}
