package com.campuslands.proyecto_spring_boot_coworking.persistence.repository;

import com.campuslands.proyecto_spring_boot_coworking.persistence.entity.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @EntityGraph(attributePaths = {"member"})
    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByMemberId(Long memberId);

    Optional<UserAccount> findByMember_Id(Long memberId);
}
